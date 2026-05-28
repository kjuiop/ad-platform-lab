package org.giglab.ad.core.role.domain.exception;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.global.exception.DomainErrorCode;

@RequiredArgsConstructor
public enum RoleErrorCode implements DomainErrorCode {
  INVALID_ROLE_NAME("ROLE-4001", "역할 이름은 ROLE_ 로 시작해야 합니다."),
  ROLE_NOT_FOUND("ROLE-4401", "역할을 찾을 수 없습니다.");

  private final String code;
  private final String message;

  @Override
  public String getCode() {
    return "";
  }

  @Override
  public String getMessage() {
    return "";
  }
}
