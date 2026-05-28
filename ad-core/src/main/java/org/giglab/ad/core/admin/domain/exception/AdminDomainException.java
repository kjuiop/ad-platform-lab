package org.giglab.ad.core.admin.domain.exception;

import org.giglab.ad.core.global.exception.DomainException;

/** 관리자 도메인 예외. */
public class AdminDomainException extends DomainException {

  /** 관리자 도메인 예외를 생성한다. */
  public AdminDomainException(AdminErrorCode errorCode) {
    super(errorCode);
  }
}
