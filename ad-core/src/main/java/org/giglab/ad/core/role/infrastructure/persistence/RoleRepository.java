package org.giglab.ad.core.role.infrastructure.persistence;

import org.giglab.ad.core.role.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/** 역할 JPA 레포지토리. */
public interface RoleRepository extends JpaRepository<Role, String> {}
