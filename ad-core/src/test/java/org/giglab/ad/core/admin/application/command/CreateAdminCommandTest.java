package org.giglab.ad.core.admin.application.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.giglab.ad.core.admin.application.dto.command.CreateAdminCommand;
import org.giglab.ad.core.admin.domain.exception.AdminDomainException;
import org.giglab.ad.core.admin.domain.exception.AdminErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CreateAdminCommand 단위 테스트")
class CreateAdminCommandTest {

  @Test
  @DisplayName("유효한 값으로 커맨드를 생성할 수 있다")
  void create_withValidPassword_succeeds() {
    CreateAdminCommand command =
        new CreateAdminCommand("admin@example.com", "홍길동", "password123", "ROLE_ADMINISTRATOR");

    assertThat(command.password()).isEqualTo("password123");
  }

  @Test
  @DisplayName("비밀번호가 8자 미만이면 INVALID_PASSWORD 예외가 발생한다")
  void create_shortPassword_throwsInvalidPasswordException() {
    assertThatThrownBy(
            () ->
                new CreateAdminCommand("admin@example.com", "홍길동", "1234567", "ROLE_ADMINISTRATOR"))
        .isInstanceOf(AdminDomainException.class)
        .hasMessage("비밀번호가 유효하지 않습니다.")
        .satisfies(
            ex ->
                assertThat(((AdminDomainException) ex).getErrorCode())
                    .isEqualTo(AdminErrorCode.INVALID_PASSWORD));
  }

  @Test
  @DisplayName("유효하지 않은 역할이면 INVALID_ROLE 예외가 발생한다")
  void create_invalidRole_throwsInvalidRoleException() {
    assertThatThrownBy(
            () -> new CreateAdminCommand("admin@example.com", "홍길동", "password123", "INVALID"))
        .isInstanceOf(AdminDomainException.class)
        .hasMessage("유효하지 않은 역할입니다.")
        .satisfies(
            ex ->
                assertThat(((AdminDomainException) ex).getErrorCode())
                    .isEqualTo(AdminErrorCode.INVALID_ROLE));
  }
}
