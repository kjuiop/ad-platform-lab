package org.giglab.ad.core.admin.domain.exception;

/** 관리자 도메인 예외. */
public class AdminDomainException extends RuntimeException {

  private final AdminErrorCode errorCode;

  /** 생성자. */
  public AdminDomainException(AdminErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public AdminErrorCode getErrorCode() {
    return errorCode;
  }
}
