package org.giglab.ad.core.role.application.port;

import java.util.Optional;
import org.giglab.ad.core.role.domain.entity.Role;

/** 역할 조회 포트. */
public interface RoleLoadPort {
  /** 이름으로 역할을 조회한다. */
  Optional<Role> findByName(String name);
}
