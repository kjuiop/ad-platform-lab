package org.giglab.ad.admin.api.controller.exception;

import org.giglab.ad.core.global.exception.DomainErrorCode;
import org.springframework.http.HttpStatus;

/** 도메인 에러 코드를 HTTP 상태로 변환하는 유틸리티. */
public class DomainHttpStatusResolver {

  private DomainHttpStatusResolver() {}

  /**
   * 도메인 에러 코드를 HTTP 상태 코드로 변환한다.
   *
   * @param errorCode 도메인 에러 코드
   * @return HTTP 상태 코드
   */
  public static HttpStatus resolve(DomainErrorCode errorCode) {
    String code = errorCode.getCode(); // e.g. "ADMIN-4001"
    int hyphenIndex = code.indexOf('-');
    if (hyphenIndex < 0 || hyphenIndex == code.length() - 1) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    int numeric;
    try {
      numeric = Integer.parseInt(code.substring(hyphenIndex + 1));
    } catch (NumberFormatException e) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    if (numeric >= 4000 && numeric < 4100) {
      return HttpStatus.BAD_REQUEST;
    }
    if (numeric >= 4100 && numeric < 4200) {
      return HttpStatus.UNAUTHORIZED;
    }
    if (numeric >= 4200 && numeric < 4300) {
      return HttpStatus.CONFLICT;
    }
    if (numeric >= 4300 && numeric < 4400) {
      return HttpStatus.FORBIDDEN;
    }
    if (numeric >= 4400 && numeric < 4500) {
      return HttpStatus.NOT_FOUND;
    }
    if (numeric >= 5000 && numeric < 6000) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
