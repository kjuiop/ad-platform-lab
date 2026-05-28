package org.giglab.ad.core.admin.domain.exception;

public enum AdminErrorCode {
  DUPLICATE_EMAIL("이미 등록된 이메일입니다.", 409),
  INVALID_PASSWORD("비밀번호가 유효하지 않습니다.", 400);

  private final String message;
  private final int httpStatusCode;

  AdminErrorCode(String message, int httpStatusCode) {
    this.message = message;
    this.httpStatusCode = httpStatusCode;
  }

  public String getMessage() {
    return message;
  }

  public int getHttpStatusCode() {
    return httpStatusCode;
  }
}
