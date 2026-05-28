package org.giglab.ad.core.admin.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import org.giglab.ad.core.admin.application.dto.command.CreateAdminCommand;
import org.giglab.ad.core.admin.application.dto.command.CreateAdminResult;
import org.giglab.ad.core.admin.application.usecase.CreateAdminUseCase;
import org.giglab.ad.core.admin.domain.exception.AdminDomainException;
import org.giglab.ad.core.admin.domain.exception.AdminErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("AdminService 단위 테스트")
class AdminServiceTest {

  @Mock private CreateAdminUseCase createAdminUseCase;

  @InjectMocks private AdminService adminService;

  @Test
  @DisplayName("createAdmin 호출 시 CreateAdminUseCase.execute()에 command를 위임한다")
  void createAdmin_delegatesToUseCase() {
    // given
    CreateAdminCommand command =
        new CreateAdminCommand("admin@example.com", "홍길동", "password123", "ROLE_ADMINISTRATOR");
    CreateAdminResult expected =
        new CreateAdminResult(
            1L, "admin@example.com", "홍길동", "ROLE_ADMINISTRATOR", LocalDateTime.now());
    given(createAdminUseCase.execute(command)).willReturn(expected);

    // when
    CreateAdminResult result = adminService.createAdmin(command);

    // then
    assertThat(result).isEqualTo(expected);
    verify(createAdminUseCase).execute(command);
  }

  @Test
  @DisplayName("useCase에서 AdminDomainException이 발생하면 서비스가 그대로 전파한다")
  void createAdmin_propagatesAdminDomainException() {
    // given
    CreateAdminCommand command =
        new CreateAdminCommand("dup@example.com", "홍길동", "password123", "ROLE_ADMINISTRATOR");
    given(createAdminUseCase.execute(any()))
        .willThrow(new AdminDomainException(AdminErrorCode.DUPLICATE_EMAIL));

    // when & then
    assertThatThrownBy(() -> adminService.createAdmin(command))
        .isInstanceOf(AdminDomainException.class)
        .hasMessage("이미 등록된 이메일입니다.");
  }

  @Test
  @DisplayName("createAdmin은 useCase가 반환한 결과를 그대로 반환한다")
  void createAdmin_returnsUseCaseResult() {
    // given
    CreateAdminCommand command =
        new CreateAdminCommand("admin@example.com", "홍길동", "password123", "ROLE_ADMINISTRATOR");
    LocalDateTime now = LocalDateTime.now();
    CreateAdminResult expected =
        new CreateAdminResult(42L, "admin@example.com", "홍길동", "ROLE_ADMINISTRATOR", now);
    given(createAdminUseCase.execute(command)).willReturn(expected);

    // when
    CreateAdminResult result = adminService.createAdmin(command);

    // then
    assertThat(result.id()).isEqualTo(42L);
    assertThat(result.email()).isEqualTo("admin@example.com");
    assertThat(result.name()).isEqualTo("홍길동");
    assertThat(result.role()).isEqualTo("ROLE_ADMINISTRATOR");
    assertThat(result.createdAt()).isEqualTo(now);
  }
}
