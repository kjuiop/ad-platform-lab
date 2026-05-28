package org.giglab.ad.core.admin.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.admin.application.port.AdminStorePort;
import org.giglab.ad.core.admin.domain.entity.Admin;
import org.giglab.ad.core.admin.domain.exception.AdminDomainException;
import org.giglab.ad.core.admin.domain.exception.AdminErrorCode;
import org.giglab.ad.core.admin.infrastructure.persistence.AdminRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaAdminStoreAdapter implements AdminStorePort {

  private final AdminRepository adminRepository;

  @Override
  public Admin store(Admin admin) {
    try {
      return adminRepository.saveAndFlush(admin);
    } catch (DataIntegrityViolationException e) {
      throw new AdminDomainException(AdminErrorCode.DUPLICATE_EMAIL);
    }
  }

  @Override
  public boolean existsByEmail(String email) {
    return adminRepository.existsByEmail(email);
  }
}
