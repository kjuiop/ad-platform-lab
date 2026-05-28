package org.giglab.ad.core.menu.application.port;

import java.util.Optional;
import org.giglab.ad.core.menu.domain.entity.Menu;

/** 메뉴 조회 포트. */
public interface MenuQueryPort {
  /** URL로 메뉴를 조회한다. */
  Optional<Menu> findByUrl(String url);

  /** ID로 메뉴를 조회한다. */
  Optional<Menu> findById(Long id);
}
