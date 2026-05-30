package org.giglab.ad.core.admin.infrastructure.adapter;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.admin.application.port.AdminQueryPort;
import org.giglab.ad.core.admin.domain.entity.Admin;
import org.giglab.ad.core.admin.infrastructure.persistence.AdminQueryRepository;
import org.springframework.stereotype.Component;

/** JPA 기반 관리자 조회 어댑터. */
@Component
@RequiredArgsConstructor
public class JpaAdminQueryAdapter implements AdminQueryPort {

  private final AdminQueryRepository adminQueryRepository;

  @Override
  public boolean existsByEmail(String email) {
    return adminQueryRepository.existsByEmail(email);
  }

  @Override
  public long count() {
    return adminQueryRepository.count();
  }

  @Override
  public Optional<Admin> findByEmail(String email) {
    return adminQueryRepository.findByEmail(email);
  }
}
