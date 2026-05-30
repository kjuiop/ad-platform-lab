package org.giglab.ad.core.role.infrastructure.adapter;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.role.application.port.RoleQueryPort;
import org.giglab.ad.core.role.domain.entity.Role;
import org.giglab.ad.core.role.infrastructure.persistence.RoleQueryRepository;
import org.springframework.stereotype.Component;

/** JPA 기반 역할 어댑터. */
@Component
@RequiredArgsConstructor
public class JpaRoleQueryAdapter implements RoleQueryPort {

  private final RoleQueryRepository queryRepository;

  @Override
  public Optional<Role> findByName(String name) {
    return queryRepository.findByName(name);
  }

  @Override
  public long count() {
    return queryRepository.count();
  }
}
