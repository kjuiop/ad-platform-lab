package org.giglab.ad.admin.api.facade;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.admin.api.dto.LoginRequest;
import org.giglab.ad.admin.api.dto.LoginResponse;
import org.giglab.ad.admin.api.dto.SignupRequest;
import org.giglab.ad.admin.api.dto.SignupResponse;
import org.giglab.ad.admin.api.mapper.AdminMapper;
import org.giglab.ad.admin.api.utils.JwtProvider;
import org.giglab.ad.core.admin.application.AdminService;
import org.giglab.ad.core.admin.application.dto.command.LoginAdminCommand;
import org.giglab.ad.core.admin.application.dto.command.LoginAdminResult;
import org.springframework.stereotype.Service;

/** 인증 관련 유스케이스를 조합하는 퍼사드. */
@Service
@RequiredArgsConstructor
public class AuthFacade {

  private final AdminService adminService;
  private final AdminMapper adminMapper;
  private final JwtProvider jwtProvider;

  /** 관리자 회원가입을 처리하고 가입 결과를 반환한다. */
  public SignupResponse adminSignUp(SignupRequest request) {
    var command = adminMapper.toCreateAdminCommand(request);
    var result = adminService.createAdmin(command);
    return adminMapper.toSignupResponse(result);
  }

  /** 관리자 로그인을 처리하고 JWT 토큰을 반환한다. */
  public LoginResponse login(LoginRequest request) {
    LoginAdminResult result =
        adminService.login(new LoginAdminCommand(request.email(), request.password()));
    String token = jwtProvider.generateToken(result.id(), result.role());
    return new LoginResponse(token, result.role());
  }
}
