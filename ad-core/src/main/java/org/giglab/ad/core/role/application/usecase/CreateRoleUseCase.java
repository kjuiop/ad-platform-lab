package org.giglab.ad.core.role.application.usecase;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.role.application.dto.command.CreateRoleCommand;
import org.giglab.ad.core.role.application.port.RoleQueryPort;
import org.giglab.ad.core.role.application.port.RoleStorePort;
import org.giglab.ad.core.role.domain.entity.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 역할 생성 유스케이스. */
@Service
@Transactional
@RequiredArgsConstructor
public class CreateRoleUseCase {

  private final RoleQueryPort roleQueryPort;
  private final RoleStorePort roleStorePort;

  /** 역할 생성 커맨드를 실행한다. 이미 존재하면 생성하지 않는다. */
  public void execute(CreateRoleCommand command) {
    if (roleQueryPort.findByName(command.name()).isPresent()) {
      return;
    }
    roleStorePort.store(
        Role.createRole(command.name(), command.description(), command.sortOrder()));
  }
}
