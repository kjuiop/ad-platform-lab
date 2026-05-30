package org.giglab.ad.core.admin.application.port;

import org.giglab.ad.core.admin.domain.entity.Admin;

/** 관리자 저장 포트. */
public interface AdminStorePort {

  /** 관리자를 저장한다. */
  Admin store(Admin admin);
}
