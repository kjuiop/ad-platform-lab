package org.giglab.ad.core.menu.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.menu.application.port.MenuStorePort;
import org.giglab.ad.core.menu.domain.entity.Menu;
import org.giglab.ad.core.menu.domain.entity.MenuRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 메뉴 애플리케이션 서비스. */
@Service
@RequiredArgsConstructor
public class MenuService {

  private final MenuStorePort menuStorePort;

  /** 최상위 메뉴와 역할 매핑을 초기화한다. */
  @Transactional
  public Menu initMenu(String name, String url, int sortOrder, List<String> roleNames) {
    Menu menu =
        menuStorePort.store(Menu.builder().name(name).url(url).sortOrder(sortOrder).build());
    roleNames.forEach(role -> menuStorePort.storeMenuRole(MenuRole.of(menu, role)));
    return menu;
  }

  /** 자식 메뉴와 역할 매핑을 초기화한다. */
  @Transactional
  public Menu initChildMenu(
      String name, String url, int sortOrder, List<String> roleNames, Menu parent) {
    Menu child =
        menuStorePort.store(
            Menu.builder().name(name).url(url).sortOrder(sortOrder).parent(parent).build());
    roleNames.forEach(role -> menuStorePort.storeMenuRole(MenuRole.of(child, role)));
    return child;
  }
}
