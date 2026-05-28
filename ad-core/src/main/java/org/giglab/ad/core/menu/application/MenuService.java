package org.giglab.ad.core.menu.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.menu.application.port.MenuLoadPort;
import org.giglab.ad.core.menu.application.port.MenuStorePort;
import org.giglab.ad.core.menu.domain.entity.Menu;
import org.giglab.ad.core.menu.domain.entity.MenuRole;
import org.giglab.ad.core.shared.role.RoleType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 메뉴 애플리케이션 서비스. */
@Service
@RequiredArgsConstructor
public class MenuService {

  private final MenuStorePort menuStorePort;
  private final MenuLoadPort menuLoadPort;

  /** 최상위 메뉴와 역할 매핑을 초기화한다. 이미 존재하면 기존 메뉴를 반환한다. */
  @Transactional
  public Menu initMenu(String name, String url, int sortOrder, List<String> roleNames) {
    return menuLoadPort
        .findByUrl(url)
        .orElseGet(
            () -> {
              Menu menu =
                  menuStorePort.store(
                      Menu.builder().name(name).url(url).sortOrder(sortOrder).build());
              roleNames.forEach(
                  role -> {
                    if (!RoleType.isValid(role)) {
                      throw new IllegalArgumentException("지원하지 않는 역할입니다: " + role);
                    }
                    menuStorePort.storeMenuRole(MenuRole.of(menu, role));
                  });
              return menu;
            });
  }

  /** 자식 메뉴와 역할 매핑을 초기화한다. 이미 존재하면 기존 메뉴를 반환한다. */
  @Transactional
  public Menu initChildMenu(
      String name, String url, int sortOrder, List<String> roleNames, Menu parent) {
    return menuLoadPort
        .findByUrl(url)
        .orElseGet(
            () -> {
              Menu child = Menu.builder().name(name).url(url).sortOrder(sortOrder).build();
              child.attachParent(parent);
              Menu saved = menuStorePort.store(child);
              roleNames.forEach(
                  role -> {
                    if (!RoleType.isValid(role)) {
                      throw new IllegalArgumentException("지원하지 않는 역할입니다: " + role);
                    }
                    menuStorePort.storeMenuRole(MenuRole.of(saved, role));
                  });
              return saved;
            });
  }
}
