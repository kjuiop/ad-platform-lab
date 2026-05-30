package org.giglab.ad.core.admin.application.usecase;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import org.giglab.ad.core.admin.application.dto.command.LoginAdminCommand;
import org.giglab.ad.core.admin.application.dto.command.LoginAdminResult;
import org.giglab.ad.core.admin.application.port.AdminQueryPort;
import org.giglab.ad.core.admin.domain.entity.Admin;
import org.giglab.ad.core.admin.domain.entity.AdminRole;
import org.giglab.ad.core.admin.domain.exception.AdminDomainException;
import org.giglab.ad.core.admin.domain.exception.AdminErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class LoginAdminUseCaseTest {

  @Mock AdminQueryPort adminQueryPort;
  @Mock PasswordEncoder passwordEncoder;
  @InjectMocks LoginAdminUseCase loginAdminUseCase;

  private Admin admin;

  @BeforeEach
  void setUp() {
    AdminRole adminRole = AdminRole.builder().role("ROLE_ADMINISTRATOR").build();
    admin =
        Admin.builder()
            .id(1L)
            .email("test@example.com")
            .password("$2a$encodedPassword")
            .name("홍길동")
            .primaryRole("ROLE_ADMINISTRATOR")
            .roles(List.of(adminRole))
            .build();
  }

  @Test
  @DisplayName("올바른 이메일, 비밀번호로 로그인하면 LoginAdminResult 를 반환한다.")
  void login_success() {
    // given
    given(adminQueryPort.findByEmail("test@example.com")).willReturn(Optional.of(admin));
    given(passwordEncoder.matches("rawPassword", "$2a$encodedPassword")).willReturn(true);

    // when
    LoginAdminResult result =
        loginAdminUseCase.execute(new LoginAdminCommand("test@example.com", "rawPassword"));

    // then
    assertThat(result.email()).isEqualTo("test@example.com");
    assertThat(result.role()).isEqualTo("ROLE_ADMINISTRATOR");
  }

  @Test
  @DisplayName("여러 역할이 있어도 primaryRole 을 반환한다.")
  void login_returnsPrimaryRole() {
    // given — roles 목록 첫 번째는 ROLE_ADMINISTRATOR지만 primaryRole은 ROLE_PARTNER_ADMIN
    AdminRole adminRole = AdminRole.builder().role("ROLE_ADMINISTRATOR").build();
    AdminRole partnerRole = AdminRole.builder().role("ROLE_PARTNER_ADMIN").build();
    Admin multiRoleAdmin =
        Admin.builder()
            .id(2L)
            .email("multi@example.com")
            .password("$2a$encodedPassword")
            .name("복수권한")
            .primaryRole("ROLE_PARTNER_ADMIN")
            .roles(List.of(adminRole, partnerRole))
            .build();

    given(adminQueryPort.findByEmail("multi@example.com")).willReturn(Optional.of(multiRoleAdmin));
    given(passwordEncoder.matches("rawPassword", "$2a$encodedPassword")).willReturn(true);

    // when
    LoginAdminResult result =
        loginAdminUseCase.execute(new LoginAdminCommand("multi@example.com", "rawPassword"));

    // then — roles.findFirst()가 아닌 primaryRole 기준
    assertThat(result.role()).isEqualTo("ROLE_PARTNER_ADMIN");
  }

  @Test
  @DisplayName("존재하지 않는 이메일이면 INVALID_CREDENTIALS 예외를 던진다.")
  void login_emailNotFound() {
    // given
    given(adminQueryPort.findByEmail(anyString())).willReturn(Optional.empty());

    // when & then
    assertThatThrownBy(
            () -> loginAdminUseCase.execute(new LoginAdminCommand("none@example.com", "pw")))
        .isInstanceOf(AdminDomainException.class)
        .hasMessageContaining(AdminErrorCode.INVALID_CREDENTIALS.getMessage());
  }

  @Test
  @DisplayName("비밀번호가 틀리면 INVALID_CREDENTIALS 예외를 던진다.")
  void login_wrongPassword() {
    // given
    given(adminQueryPort.findByEmail("test@example.com")).willReturn(Optional.of(admin));
    given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

    // when & then
    assertThatThrownBy(
            () -> loginAdminUseCase.execute(new LoginAdminCommand("test@example.com", "wrong")))
        .isInstanceOf(AdminDomainException.class)
        .hasMessageContaining(AdminErrorCode.INVALID_CREDENTIALS.getMessage());
  }
}
