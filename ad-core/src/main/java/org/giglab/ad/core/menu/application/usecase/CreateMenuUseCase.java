package org.giglab.ad.core.menu.application.usecase;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.menu.application.dto.command.CreateMenuCommand;
import org.giglab.ad.core.menu.application.port.MenuQueryPort;
import org.giglab.ad.core.menu.application.port.MenuStorePort;
import org.giglab.ad.core.menu.domain.entity.Menu;
import org.giglab.ad.core.menu.domain.exception.MenuDomainException;
import org.giglab.ad.core.menu.domain.exception.MenuErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 메뉴 생성 유스케이스. */
@Service
@Transactional
@RequiredArgsConstructor
public class CreateMenuUseCase {

  private final MenuQueryPort menuQueryPort;
  private final MenuStorePort menuStorePort;

  /** 메뉴 생성 커맨드를 실행한다. */
  public Long execute(CreateMenuCommand command) {
    if (menuQueryPort.findByUrl(command.url()).isPresent()) {
      throw new MenuDomainException(MenuErrorCode.DUPLICATE_URL);
    }

    Menu menu = Menu.create(command.name(), command.url(), command.sortOrder());
    command.roles().forEach(menu::addRole);

    return menuStorePort.store(menu).getId();
  }
}
