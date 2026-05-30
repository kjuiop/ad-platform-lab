package org.giglab.ad.core.admin.application.usecase;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.admin.application.dto.command.LoginAdminCommand;
import org.giglab.ad.core.admin.application.dto.command.LoginAdminResult;
import org.giglab.ad.core.admin.application.port.AdminQueryPort;
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
public class LoginAdminUseCase {

  private final AdminQueryPort adminQueryPort;
  private final PasswordEncoder passwordEncoder;

  public LoginAdminResult execute(LoginAdminCommand command) {
    Optional<Admin> findAdmin = adminQueryPort.findByEmail(command.email());
    if (findAdmin.isEmpty()) {
      throw new AdminDomainException(AdminErrorCode.INVALID_CREDENTIALS);
    }
    Admin admin = findAdmin.get();

    if (!passwordEncoder.matches(command.password(), admin.getPassword())) {
      throw new AdminDomainException(AdminErrorCode.INVALID_CREDENTIALS);
    }

    String role = admin.getRoles().stream().findFirst().map(AdminRole::getRole).orElse("");

    return new LoginAdminResult(admin.getEmail(), role);
  }
}
