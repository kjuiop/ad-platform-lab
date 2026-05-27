package org.giglab.ad.core.admin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.giglab.ad.core.admin.domain.exception.AdminDomainException;
import org.giglab.ad.core.admin.domain.exception.AdminErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayName("AdminDomainException 단위 테스트")
class AdminDomainExceptionTest {

  @Test
  @DisplayName("DUPLICATE_EMAIL 에러코드로 예외를 생성하면 메시지와 HTTP 상태코드가 일치한다")
  void exception_duplicateEmail_hasCorrectMessageAndStatus() {
    AdminDomainException exception = new AdminDomainException(AdminErrorCode.DUPLICATE_EMAIL);

    assertThat(exception.getMessage()).isEqualTo("이미 등록된 이메일입니다.");
    assertThat(exception.getErrorCode().getHttpStatusCode()).isEqualTo(409);
  }

  @Test
  @DisplayName("INVALID_PASSWORD 에러코드로 예외를 생성하면 메시지와 HTTP 상태코드가 일치한다")
  void exception_invalidPassword_hasCorrectMessageAndStatus() {
    AdminDomainException exception = new AdminDomainException(AdminErrorCode.INVALID_PASSWORD);

    assertThat(exception.getMessage()).isEqualTo("비밀번호가 유효하지 않습니다.");
    assertThat(exception.getErrorCode().getHttpStatusCode()).isEqualTo(400);
  }

  @Test
  @DisplayName("예외는 RuntimeException을 상속한다")
  void exception_isRuntimeException() {
    AdminDomainException exception = new AdminDomainException(AdminErrorCode.DUPLICATE_EMAIL);

    assertThat(exception).isInstanceOf(RuntimeException.class);
  }

  @ParameterizedTest
  @EnumSource(AdminErrorCode.class)
  @DisplayName("모든 AdminErrorCode에 대해 예외 생성 시 errorCode를 보존한다")
  void exception_preservesErrorCode(AdminErrorCode errorCode) {
    AdminDomainException exception = new AdminDomainException(errorCode);

    assertThat(exception.getErrorCode()).isEqualTo(errorCode);
    assertThat(exception.getMessage()).isEqualTo(errorCode.getMessage());
  }
}
