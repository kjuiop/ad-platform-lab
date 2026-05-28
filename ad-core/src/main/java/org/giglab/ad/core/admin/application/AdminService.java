package org.giglab.ad.core.admin.application;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.admin.application.dto.command.CreateAdminCommand;
import org.giglab.ad.core.admin.application.dto.command.CreateAdminResult;
import org.giglab.ad.core.admin.application.usecase.CreateAdminUseCase;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

  private final CreateAdminUseCase createAdminUseCase;

  public CreateAdminResult createAdmin(CreateAdminCommand command) {
    return createAdminUseCase.execute(command);
  }
}
