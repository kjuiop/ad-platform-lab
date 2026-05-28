package org.giglab.ad.core.admin.domain.exception;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.global.exception.DomainErrorCode;

@RequiredArgsConstructor
public enum AdminErrorCode implements DomainErrorCode {
  DUPLICATE_EMAIL("ADMIN-4201", "이미 등록된 이메일입니다."),
  INVALID_PASSWORD("ADMIN-4001", "비밀번호가 유효하지 않습니다.");

  private final String code;
  private final String message;

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
