package org.giglab.ad.core.menu.application.port;

import org.giglab.ad.core.menu.domain.entity.Menu;
import org.giglab.ad.core.menu.domain.entity.MenuRole;

/** 메뉴 저장 포트. */
public interface MenuStorePort {
  /** 메뉴를 저장한다. */
  Menu store(Menu menu);

  /** 메뉴-역할 매핑을 저장한다. */
  void storeMenuRole(MenuRole menuRole);
}
