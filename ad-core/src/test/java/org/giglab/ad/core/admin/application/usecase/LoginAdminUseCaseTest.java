package org.giglab.ad.core.admin.application.usecase;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import org.giglab.ad.core.admin.application.dto.command.LoginAdminCommand;
import org.giglab.ad.core.admin.application.dto.command.LoginAdminResult;
import org.giglab.ad.core.admin.application.port.AdminQueryPort;
import org.giglab.ad.core.admin.domain.entity.Admin;
import org.giglab.ad.core.admin.domain.entity.AdminRole;
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
}
