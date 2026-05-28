package org.giglab.ad.core.menu.infrastructure.persistence;

import org.giglab.ad.core.menu.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

/** 메뉴 JPA 레포지토리. */
public interface MenuRepository extends JpaRepository<Menu, Long> {}
