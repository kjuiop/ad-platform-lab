package org.giglab.ad.core.admin.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.giglab.ad.core.admin.application.dto.command.CreateAdminCommand;
import org.giglab.ad.core.admin.application.dto.command.CreateAdminResult;
import org.giglab.ad.core.admin.application.port.AdminStorePort;
import org.giglab.ad.core.admin.application.usecase.CreateAdminUseCase;
import org.giglab.ad.core.admin.domain.entity.Admin;
import org.giglab.ad.core.admin.domain.exception.AdminDomainException;
import org.giglab.ad.core.admin.domain.exception.AdminErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateAdminUseCase 단위 테스트")
class CreateAdminUseCaseTest {

  @Mock private PasswordEncoder passwordEncoder;
  @Mock private AdminStorePort adminStorePort;

  @InjectMocks private CreateAdminUseCase createAdminUseCase;

  @Test
  @DisplayName("중복되지 않은 이메일로 관리자를 생성하면 CreateAdminResult를 반환한다")
  void execute_success_returnsCreateAdminResult() {
    // given
    CreateAdminCommand command =
        new CreateAdminCommand("admin@example.com", "홍길동", "password123", "ROLE_ADMINISTRATOR");
    given(adminStorePort.existsByEmail("admin@example.com")).willReturn(false);
    given(passwordEncoder.encode("password123")).willReturn("$2a$encoded");
    Admin savedAdmin = createAdminWithId("admin@example.com", "홍길동", "$2a$encoded");
    given(adminStorePort.store(any(Admin.class))).willReturn(savedAdmin);

    // when
    CreateAdminResult result = createAdminUseCase.execute(command);

    // then — id는 DB 자동 생성이므로 null 허용, createdAt은 JPA 영속화 시 채워지므로 단위 테스트에서 검증 제외
    assertThat(result.email()).isEqualTo("admin@example.com");
    assertThat(result.name()).isEqualTo("홍길동");
    assertThat(result.role()).isEqualTo("ROLE_ADMINISTRATOR");
  }

  @Test
  @DisplayName("중복 이메일로 관리자를 생성하면 DUPLICATE_EMAIL 예외가 발생한다")
  void execute_duplicateEmail_throwsAdminDomainException() {
    // given
    CreateAdminCommand command =
        new CreateAdminCommand("dup@example.com", "홍길동", "password123", "ROLE_ADMINISTRATOR");
    given(adminStorePort.existsByEmail("dup@example.com")).willReturn(true);

    // when & then
    assertThatThrownBy(() -> createAdminUseCase.execute(command))
        .isInstanceOf(AdminDomainException.class)
        .hasMessage("이미 등록된 이메일입니다.")
        .satisfies(
            ex ->
                assertThat(((AdminDomainException) ex).getErrorCode())
                    .isEqualTo(AdminErrorCode.DUPLICATE_EMAIL));
  }

  @Test
  @DisplayName("중복 이메일이면 store()가 호출되지 않는다")
  void execute_duplicateEmail_doesNotCallStore() {
    // given
    CreateAdminCommand command =
        new CreateAdminCommand("dup@example.com", "홍길동", "password123", "ROLE_ADMINISTRATOR");
    given(adminStorePort.existsByEmail("dup@example.com")).willReturn(true);

    // when
    assertThatThrownBy(() -> createAdminUseCase.execute(command))
        .isInstanceOf(AdminDomainException.class);

    // then
    verify(adminStorePort, never()).store(any(Admin.class));
  }

  @Test
  @DisplayName("중복 이메일이면 passwordEncoder.encode()가 호출되지 않는다")
  void execute_duplicateEmail_doesNotEncodePassword() {
    // given
    CreateAdminCommand command =
        new CreateAdminCommand("dup@example.com", "홍길동", "password123", "ROLE_ADMINISTRATOR");
    given(adminStorePort.existsByEmail("dup@example.com")).willReturn(true);

    // when
    assertThatThrownBy(() -> createAdminUseCase.execute(command))
        .isInstanceOf(AdminDomainException.class);

    // then
    verify(passwordEncoder, never()).encode(anyString());
  }

  @Test
  @DisplayName("관리자 생성 시 비밀번호는 인코딩된 값으로 저장된다")
  void execute_success_passwordIsEncoded() {
    // given
    given(adminStorePort.existsByEmail("admin@example.com")).willReturn(false);
    given(passwordEncoder.encode("plainPassword")).willReturn("$2a$encodedPassword");
    Admin savedAdmin = createAdminWithId("admin@example.com", "홍길동", "$2a$encodedPassword");
    given(adminStorePort.store(any(Admin.class))).willReturn(savedAdmin);
    CreateAdminCommand command =
        new CreateAdminCommand("admin@example.com", "홍길동", "plainPassword", "ROLE_ADMINISTRATOR");

    // when
    createAdminUseCase.execute(command);

    // then — store는 인코딩된 비밀번호를 가진 Admin으로 호출되어야 한다
    verify(passwordEncoder).encode("plainPassword");
    verify(adminStorePort).store(any(Admin.class));
  }

  /** 테스트용 Admin 객체 생성 헬퍼 — id는 JPA 영속화 전에는 null이므로 create()로 생성. */
  private Admin createAdminWithId(String email, String name, String password) {
    return Admin.create(email, name, password);
  }
}
