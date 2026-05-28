package org.giglab.ad.core.admin.domain.exception;

import org.giglab.ad.core.global.exception.DomainException;

public class AdminDomainException extends DomainException {

  public AdminDomainException(AdminErrorCode errorCode) {
    super(errorCode);
  }
}
