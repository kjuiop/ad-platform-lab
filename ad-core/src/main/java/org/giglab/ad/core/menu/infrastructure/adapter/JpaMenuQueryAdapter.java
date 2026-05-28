package org.giglab.ad.core.menu.infrastructure.adapter;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.menu.application.port.MenuQueryPort;
import org.giglab.ad.core.menu.domain.entity.Menu;
import org.giglab.ad.core.menu.infrastructure.persistence.MenuQueryRepository;
import org.springframework.stereotype.Component;

/** JPA 기반 메뉴 저장/조회 어댑터. */
@Component
@RequiredArgsConstructor
public class JpaMenuQueryAdapter implements MenuQueryPort {

  private final MenuQueryRepository menuQueryRepository;

  @Override
  public Optional<Menu> findByUrl(String url) {
    return menuQueryRepository.findByUrl(url);
  }

  @Override
  public Optional<Menu> findById(Long id) {
    return menuQueryRepository.findById(id);
  }
}
