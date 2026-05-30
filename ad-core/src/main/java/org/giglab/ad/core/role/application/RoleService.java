package org.giglab.ad.core.role.application;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.role.application.dto.command.CreateRoleCommand;
import org.giglab.ad.core.role.application.port.RoleQueryPort;
import org.giglab.ad.core.role.application.usecase.CreateRoleUseCase;
import org.springframework.stereotype.Service;

/** 역할 애플리케이션 서비스. */
@Service
@RequiredArgsConstructor
public class RoleService {

  private final CreateRoleUseCase createRoleUseCase;
  private final RoleQueryPort roleQueryPort;

  /** 역할을 초기화한다. 이미 존재하면 생성하지 않는다. */
  public void initRole(String name, String description, int sortOrder) {
    createRoleUseCase.execute(new CreateRoleCommand(name, description, sortOrder));
  }

  /** 역할 데이터가 하나 이상 존재하는지 확인한다. */
  public boolean hasData() {
    return roleQueryPort.count() > 0;
  }
}
