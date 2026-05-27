package org.giglab.ad.admin.api.controller;

import jakarta.validation.Valid;
import java.util.Map;
import org.giglab.ad.admin.api.dto.SignupRequest;
import org.giglab.ad.admin.api.dto.SignupResponse;
import org.giglab.ad.admin.api.facade.AuthFacade;
import org.giglab.ad.core.admin.domain.exception.AdminDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 인증 API 컨트롤러. */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthFacade authFacade;

  /** 생성자. */
  public AuthController(AuthFacade authFacade) {
    this.authFacade = authFacade;
  }

  /** 회원가입 API. */
  @PostMapping("/signup")
  public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
    SignupResponse response = authFacade.adminSignUp(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /** 도메인 예외를 에러코드에 따른 HTTP 상태로 변환. */
  @ExceptionHandler(AdminDomainException.class)
  public ResponseEntity<Map<String, String>> handleAdminDomainException(AdminDomainException ex) {
    return ResponseEntity.status(ex.getErrorCode().getHttpStatusCode())
        .body(Map.of("error", ex.getMessage()));
  }
}
