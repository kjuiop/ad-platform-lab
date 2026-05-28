package org.giglab.ad.core.admin.domain.exception;

public class AdminDomainException extends RuntimeException {

  private final AdminErrorCode errorCode;

  public AdminDomainException(AdminErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public AdminErrorCode getErrorCode() {
    return errorCode;
  }
}
