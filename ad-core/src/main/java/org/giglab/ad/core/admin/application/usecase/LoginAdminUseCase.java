package org.giglab.ad.core.admin.application.usecase;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.admin.application.dto.command.LoginAdminCommand;
import org.giglab.ad.core.admin.application.dto.command.LoginAdminResult;
import org.giglab.ad.core.admin.application.port.AdminQueryPort;
import org.giglab.ad.core.admin.domain.entity.Admin;
import org.giglab.ad.core.admin.domain.exception.AdminDomainException;
import org.giglab.ad.core.admin.domain.exception.AdminErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginAdminUseCase {

  private final AdminQueryPort adminQueryPort;
  private final PasswordEncoder passwordEncoder;

  public LoginAdminResult execute(LoginAdminCommand command) {
    Admin admin =
        adminQueryPort
            .findByEmail(command.email())
            .orElseThrow(() -> new AdminDomainException(AdminErrorCode.INVALID_CREDENTIALS));

    if (!passwordEncoder.matches(command.password(), admin.getPassword())) {
      throw new AdminDomainException(AdminErrorCode.INVALID_CREDENTIALS);
    }

    return new LoginAdminResult(admin.getEmail(), admin.getPrimaryRole());
  }
}
