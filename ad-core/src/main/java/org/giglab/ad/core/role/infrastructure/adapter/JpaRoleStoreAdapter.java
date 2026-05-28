package org.giglab.ad.core.role.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.role.application.port.RoleStorePort;
import org.giglab.ad.core.role.domain.entity.Role;
import org.giglab.ad.core.role.infrastructure.persistence.RoleRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

/** JPA 기반 역할 어댑터. */
@Component
@RequiredArgsConstructor
public class JpaRoleStoreAdapter implements RoleStorePort {

  private final RoleRepository roleRepository;

  @Override
  public Role store(Role role) {
    try {
      return roleRepository.save(role);
    } catch (DataIntegrityViolationException e) {
      return role;
    }
  }
}
