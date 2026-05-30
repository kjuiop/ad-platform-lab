package org.giglab.ad.core.menu.application;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.menu.application.dto.command.CreateChildMenuCommand;
import org.giglab.ad.core.menu.application.dto.command.CreateMenuCommand;
import org.giglab.ad.core.menu.application.port.MenuQueryPort;
import org.giglab.ad.core.menu.application.usecase.CreateChildMenuUseCase;
import org.giglab.ad.core.menu.application.usecase.CreateMenuUseCase;
import org.springframework.stereotype.Service;

/** 메뉴 애플리케이션 서비스. */
@Service
@RequiredArgsConstructor
public class MenuService {

  private final CreateMenuUseCase createMenuUseCase;
  private final CreateChildMenuUseCase createChildMenuUseCase;
  private final MenuQueryPort menuQueryPort;

  /** 최상위 메뉴와 역할 매핑을 초기화한다. 이미 존재하면 기존 메뉴를 반환한다. */
  public Long createMenu(CreateMenuCommand command) {
    return createMenuUseCase.execute(command);
  }

  /** 자식 메뉴와 역할 매핑을 초기화한다. 이미 존재하면 기존 메뉴를 반환한다. */
  public void createChildMenu(CreateChildMenuCommand command) {
    createChildMenuUseCase.execute(command);
  }

  /** 메뉴 데이터가 하나 이상 존재하는지 확인한다. */
  public boolean hasData() {
    return menuQueryPort.count() > 0;
  }
}
