package org.giglab.ad.core.role.domain.exception;

import org.giglab.ad.core.global.exception.DomainException;

/** 역할 도메인 예외. */
public class RoleDomainException extends DomainException {

  /** 역할 도메인 예외를 생성한다. */
  public RoleDomainException(RoleErrorCode errorCode) {
    super(errorCode);
  }
}
