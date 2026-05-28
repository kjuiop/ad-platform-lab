package org.giglab.ad.core.global.exception;

import lombok.Getter;

/** 도메인 예외 기반 클래스. */
@Getter
public abstract class DomainException extends RuntimeException {

  private final DomainErrorCode errorCode;

  /** 에러 코드로 도메인 예외를 생성한다. */
  protected DomainException(DomainErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  /** 에러 코드와 원인 예외로 도메인 예외를 생성한다. */
  protected DomainException(DomainErrorCode errorCode, Throwable cause) {
    super(errorCode.getMessage(), cause);
    this.errorCode = errorCode;
  }
}
