package org.giglab.ad.core.menu.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.menu.application.port.MenuStorePort;
import org.giglab.ad.core.menu.domain.entity.Menu;
import org.giglab.ad.core.menu.domain.entity.MenuRole;
import org.giglab.ad.core.menu.infrastructure.persistence.MenuRepository;
import org.giglab.ad.core.menu.infrastructure.persistence.MenuRoleRepository;
import org.springframework.stereotype.Component;

/** JPA 기반 메뉴 저장 어댑터. */
@Component
@RequiredArgsConstructor
public class JpaMenuAdapter implements MenuStorePort {

  private final MenuRepository menuRepository;
  private final MenuRoleRepository menuRoleRepository;

  @Override
  public Menu store(Menu menu) {
    return menuRepository.save(menu);
  }

  @Override
  public void storeMenuRole(MenuRole menuRole) {
    menuRoleRepository.save(menuRole);
  }
}
