package org.giglab.ad.core.admin.infrastructure.persistence;

import org.giglab.ad.core.admin.domain.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/** 관리자 리포지토리. */
public interface AdminRepository extends JpaRepository<Admin, Long> {}
