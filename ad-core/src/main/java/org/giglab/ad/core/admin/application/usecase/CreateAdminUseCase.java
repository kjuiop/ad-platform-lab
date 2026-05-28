package org.giglab.ad.core.admin.application.usecase;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.admin.application.dto.command.CreateAdminCommand;
import org.giglab.ad.core.admin.application.dto.command.CreateAdminResult;
import org.giglab.ad.core.admin.application.port.AdminStorePort;
import org.giglab.ad.core.admin.domain.entity.Admin;
import org.giglab.ad.core.admin.domain.entity.AdminRole;
import org.giglab.ad.core.admin.domain.exception.AdminDomainException;
import org.giglab.ad.core.admin.domain.exception.AdminErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateAdminUseCase {

  private final PasswordEncoder passwordEncoder;
  private final AdminStorePort adminStorePort;

  public CreateAdminResult execute(CreateAdminCommand command) {
    if (adminStorePort.existsByEmail(command.email())) {
      throw new AdminDomainException(AdminErrorCode.DUPLICATE_EMAIL);
    }
    String encodedPassword = passwordEncoder.encode(command.password());
    Admin admin =
        Admin.create(command.email(), command.name(), encodedPassword, AdminRole.ADMINISTRATOR);
    Admin saved = adminStorePort.store(admin);
    return new CreateAdminResult(
        saved.getId(),
        saved.getEmail(),
        saved.getName(),
        saved.getRole().name(),
        saved.getCreatedAt());
  }
}
