package org.giglab.ad.core.role.domain.exception;

import org.giglab.ad.core.global.exception.DomainException;

public class RoleDomainException extends DomainException {

  public RoleDomainException(RoleErrorCode errorCode) {
    super(errorCode);
  }
}
