package org.giglab.ad.core.menu.domain.exception;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.global.exception.DomainErrorCode;

/** 메뉴 에러 코드. */
@RequiredArgsConstructor
public enum MenuErrorCode implements DomainErrorCode {
  DUPLICATE_URL("MENU-4201", "이미 존재하는 URL입니다."),
  PARENT_MENU_NOT_FOUND("MENU-4401", "부모 메뉴를 찾을 수 없습니다.");

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
