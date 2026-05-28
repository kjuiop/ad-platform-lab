package org.giglab.ad.core.admin.application;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.admin.application.dto.command.CreateAdminCommand;
import org.giglab.ad.core.admin.application.dto.command.CreateAdminResult;
import org.giglab.ad.core.admin.application.port.AdminStorePort;
import org.giglab.ad.core.admin.application.usecase.CreateAdminUseCase;
import org.giglab.ad.core.admin.domain.entity.Admin;
import org.giglab.ad.core.admin.domain.entity.AdminRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 관리자 애플리케이션 서비스. */
@Service
@RequiredArgsConstructor
public class AdminService {

  private final CreateAdminUseCase createAdminUseCase;
  private final AdminStorePort adminStorePort;

  /** 관리자 회원가입을 처리한다. */
  public CreateAdminResult createAdmin(CreateAdminCommand command) {
    return createAdminUseCase.execute(command);
  }

  /** 초기 관리자 계정을 생성한다. 이미 존재하면 무시한다. */
  @Transactional
  public void initAdmin(String email, String encodedPassword, String name, AdminRole role) {
    if (adminStorePort.existsByEmail(email)) {
      return;
    }
    adminStorePort.store(Admin.create(email, name, encodedPassword, role));
  }
}
