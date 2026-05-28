package org.giglab.ad.core.admin.application.dto.command;

import org.giglab.ad.core.admin.domain.exception.AdminDomainException;
import org.giglab.ad.core.admin.domain.exception.AdminErrorCode;

/** 관리자 생성 커맨드. */
public record CreateAdminCommand(String email, String name, String password, String role) {
  /** 패스워드 유효성을 검증한다. */
  public CreateAdminCommand {
    if (password == null || password.length() < 8) {
      throw new AdminDomainException(AdminErrorCode.INVALID_PASSWORD);
    }
  }
}
