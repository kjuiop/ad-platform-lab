package org.giglab.ad.admin.api.controller.exception;

import static org.giglab.ad.admin.api.controller.exception.CommonErrorCode.INVALID_REQUEST;
import static org.giglab.ad.admin.api.controller.exception.CommonErrorCode.UNEXPECTED_ERROR;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.giglab.ad.admin.api.response.ApiResponse;
import org.giglab.ad.core.global.exception.DomainErrorCode;
import org.giglab.ad.core.global.exception.DomainException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** 전역 예외 처리 어드바이스. */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

  /** 도메인 예외를 처리한다. */
  @ExceptionHandler(DomainException.class)
  public ResponseEntity<ApiResponse<Void>> handleDomainException(DomainException ex) {
    DomainErrorCode errorCode = ex.getErrorCode();
    HttpStatus status = DomainHttpStatusResolver.resolve(errorCode);
    logByStatus(status, errorCode.getCode(), ex.getMessage());
    return ResponseEntity.status(status)
        .body(ApiResponse.error(errorCode.getCode(), errorCode.getMessage()));
  }

  /** Bean Validation 실패 예외를 처리한다. */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex) {
    String message =
        ex.getBindingResult().getFieldErrors().stream()
            .findFirst()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .orElse(INVALID_REQUEST.getMessage());

    log.warn("MethodArgumentNotValid: {}", ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error(INVALID_REQUEST.getCode(), message));
  }

  /** 제약 조건 위반 예외를 처리한다. */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(
      ConstraintViolationException ex) {
    String message =
        ex.getConstraintViolations().stream()
            .findFirst()
            .map(ConstraintViolation::getMessage)
            .orElse(INVALID_REQUEST.getMessage());

    log.warn("ConstraintViolation: {}", message);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error(INVALID_REQUEST.getCode(), message));
  }

  /** HTTP 메시지 파싱 불가 예외를 처리한다. */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error(INVALID_REQUEST.getCode(), INVALID_REQUEST.getMessage()));
  }

  /** 예상치 못한 예외를 처리한다. */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleUnexpected(Exception ex) {
    log.error("Unexpected exception occurred.", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.error(UNEXPECTED_ERROR.getCode(), UNEXPECTED_ERROR.getMessage()));
  }

  private void logByStatus(HttpStatus status, String code, String message) {
    if (status.is4xxClientError()) {
      log.warn("AdminDomainException client error, code={}, message={}", code, message);
    } else {
      log.error("AdminDomainException server error, code={}, message={}", code, message);
    }
  }
}
