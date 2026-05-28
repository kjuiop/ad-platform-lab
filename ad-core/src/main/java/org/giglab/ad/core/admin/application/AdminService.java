package org.giglab.ad.core.admin.application;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.admin.application.dto.command.CreateAdminCommand;
import org.giglab.ad.core.admin.application.dto.command.CreateAdminResult;
import org.giglab.ad.core.admin.application.port.AdminStorePort;
import org.giglab.ad.core.admin.application.usecase.CreateAdminUseCase;
import org.springframework.stereotype.Service;

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

  /** 관리자 초기 데이터를 생성한다. 이미 존재하면 생성하지 않는다. */
  public void initAdmin(CreateAdminCommand command) {
    if (adminStorePort.existsByEmail(command.email())) {
      return;
    }
    createAdminUseCase.execute(command);
  }
}
