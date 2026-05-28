package org.giglab.ad.core.menu.infrastructure.persistence;

import org.giglab.ad.core.menu.domain.entity.MenuRole;
import org.springframework.data.jpa.repository.JpaRepository;

/** 메뉴-역할 JPA 레포지토리. */
public interface MenuRoleRepository extends JpaRepository<MenuRole, Long> {}
