package org.giglab.ad.core.global.exception;

/** 도메인 에러 코드 인터페이스. */
public interface DomainErrorCode {

  /** 에러 코드를 반환한다. */
  String getCode();

  /** 에러 메시지를 반환한다. */
  String getMessage();
}
