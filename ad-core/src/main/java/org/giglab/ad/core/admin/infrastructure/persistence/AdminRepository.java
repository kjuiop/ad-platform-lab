package org.giglab.ad.core.admin.infrastructure.persistence;

import java.util.Optional;
import org.giglab.ad.core.admin.domain.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/** 관리자 리포지토리. */
public interface AdminRepository extends JpaRepository<Admin, Long> {

  /** 이메일로 Admin 조회. */
  Optional<Admin> findByEmail(String email);

  /** 이메일 존재 여부 확인. */
  boolean existsByEmail(String email);
}
