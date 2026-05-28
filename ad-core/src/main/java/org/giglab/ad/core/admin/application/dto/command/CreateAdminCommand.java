package org.giglab.ad.core.admin.application.dto.command;

import org.giglab.ad.core.admin.domain.exception.AdminDomainException;
import org.giglab.ad.core.admin.domain.exception.AdminErrorCode;

public record CreateAdminCommand(String email, String name, String password) {
  public CreateAdminCommand {
    if (password == null || password.length() < 8) {
      throw new AdminDomainException(AdminErrorCode.INVALID_PASSWORD);
    }
  }
}
