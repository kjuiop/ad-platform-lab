package org.giglab.ad.admin.api.facade;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.admin.api.dto.SignupRequest;
import org.giglab.ad.admin.api.dto.SignupResponse;
import org.giglab.ad.admin.api.mapper.AdminMapper;
import org.giglab.ad.core.admin.application.AdminService;
import org.springframework.stereotype.Service;

/** 인증 관련 유스케이스를 조합하는 퍼사드. */
@Service
@RequiredArgsConstructor
public class AuthFacade {

  private final AdminService adminService;
  private final AdminMapper adminMapper;

  /** 관리자 회원가입을 처리하고 가입 결과를 반환한다. */
  public SignupResponse adminSignUp(SignupRequest request) {
    var command = adminMapper.toCreateAdminCommand(request);
    var result = adminService.createAdmin(command);
    return adminMapper.toSignupResponse(result);
  }
}
