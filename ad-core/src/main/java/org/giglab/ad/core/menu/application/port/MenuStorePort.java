package org.giglab.ad.core.menu.application.port;

import org.giglab.ad.core.menu.domain.entity.Menu;

/** 메뉴 저장 포트. */
public interface MenuStorePort {
  /** 메뉴를 저장한다. */
  Menu store(Menu menu);
}
