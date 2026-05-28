package org.giglab.ad.core.role.application;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.role.application.port.RoleLoadPort;
import org.giglab.ad.core.role.application.port.RoleStorePort;
import org.giglab.ad.core.role.domain.entity.Role;
import org.giglab.ad.core.role.domain.exception.RoleDomainException;
import org.giglab.ad.core.role.domain.exception.RoleErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 역할 애플리케이션 서비스. */
@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleStorePort roleStorePort;
  private final RoleLoadPort roleLoadPort;

  /** 역할을 초기화한다. 이미 존재하면 기존 역할을 반환한다. */
  @Transactional
  public Role initRole(String name, String description, int sortOrder) {
    return roleLoadPort
        .findByName(name)
        .orElseGet(() -> roleStorePort.store(Role.createRole(name, description, sortOrder)));
  }

  /** 이름으로 역할을 조회한다. 없으면 예외를 던진다. */
  public Role findByName(String name) {
    return roleLoadPort
        .findByName(name)
        .orElseThrow(() -> new RoleDomainException(RoleErrorCode.ROLE_NOT_FOUND));
  }
}
