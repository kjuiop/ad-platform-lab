package org.giglab.ad.core.admin.application.port;

import org.giglab.ad.core.admin.domain.entity.Admin;

/** 관리자 저장 포트. */
public interface AdminStorePort {

  /** 이메일 존재 여부를 반환한다. */
  boolean existsByEmail(String email);

  /** 관리자를 저장한다. */
  Admin store(Admin admin);
}
