package org.giglab.ad.core.menu.application.usecase;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.menu.application.dto.command.CreateChildMenuCommand;
import org.giglab.ad.core.menu.application.port.MenuQueryPort;
import org.giglab.ad.core.menu.application.port.MenuStorePort;
import org.giglab.ad.core.menu.domain.entity.Menu;
import org.giglab.ad.core.menu.domain.exception.MenuDomainException;
import org.giglab.ad.core.menu.domain.exception.MenuErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 자식 메뉴 생성 유스케이스. */
@Service
@Transactional
@RequiredArgsConstructor
public class CreateChildMenuUseCase {

  private final MenuQueryPort menuQueryPort;
  private final MenuStorePort menuStorePort;

  /** 자식 메뉴 생성 커맨드를 실행한다. */
  public void execute(CreateChildMenuCommand command) {
    if (menuQueryPort.findByUrl(command.url()).isPresent()) {
      throw new MenuDomainException(MenuErrorCode.DUPLICATE_URL);
    }

    Optional<Menu> findParent = menuQueryPort.findById(command.parentId());
    if (findParent.isEmpty()) {
      throw new MenuDomainException(MenuErrorCode.PARENT_MENU_NOT_FOUND);
    }

    Menu parent = findParent.get();

    Menu menu = Menu.create(command.name(), command.url(), command.sortOrder());
    menu.attachParent(parent);
    command.roles().forEach(menu::addRole);

    menuStorePort.store(menu);
  }
}
