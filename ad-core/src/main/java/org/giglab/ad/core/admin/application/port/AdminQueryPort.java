package org.giglab.ad.core.admin.application.port;

/** 관리자 조회 포트. */
public interface AdminQueryPort {

  /** 이메일로 관리자 존재 여부를 반환한다. */
  boolean existsByEmail(String email);
}
