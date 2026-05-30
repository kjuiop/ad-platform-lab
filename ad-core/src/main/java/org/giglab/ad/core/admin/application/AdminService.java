package org.giglab.ad.core.admin.application;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.admin.application.dto.command.CreateAdminCommand;
import org.giglab.ad.core.admin.application.dto.command.CreateAdminResult;
import org.giglab.ad.core.admin.application.port.AdminQueryPort;
import org.giglab.ad.core.admin.application.usecase.CreateAdminUseCase;
import org.springframework.stereotype.Service;

/** 관리자 애플리케이션 서비스. */
@Service
@RequiredArgsConstructor
public class AdminService {

  private final CreateAdminUseCase createAdminUseCase;
  private final AdminQueryPort adminQueryPort;

  /** 관리자 회원가입을 처리한다. */
  public CreateAdminResult createAdmin(CreateAdminCommand command) {
    return createAdminUseCase.execute(command);
  }

  /** 관리자 데이터가 하나 이상 존재하는지 확인한다. */
  public boolean hasData() {
    return adminQueryPort.count() > 0;
  }
}
