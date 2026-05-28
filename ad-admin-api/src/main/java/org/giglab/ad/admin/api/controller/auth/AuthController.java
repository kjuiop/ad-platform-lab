package org.giglab.ad.admin.api.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.giglab.ad.admin.api.dto.SignupRequest;
import org.giglab.ad.admin.api.dto.SignupResponse;
import org.giglab.ad.admin.api.facade.AuthFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 인증 API 컨트롤러. */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthFacade authFacade;

  @PostMapping("/signup")
  public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
    SignupResponse response = authFacade.adminSignUp(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
