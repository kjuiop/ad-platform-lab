package org.giglab.ad.core.menu.domain.exception;

import org.giglab.ad.core.global.exception.DomainException;

/** 메뉴 도메인 예외. */
public class MenuDomainException extends DomainException {

  /** 메뉴 도메인 예외를 생성한다. */
  public MenuDomainException(MenuErrorCode errorCode) {
    super(errorCode);
  }
}
