package org.giglab.ad.core.role.application.port;

import org.giglab.ad.core.role.domain.entity.Role;

/** 역할 저장 포트. */
public interface RoleStorePort {
  /** 역할을 저장한다. */
  Role store(Role role);
}
