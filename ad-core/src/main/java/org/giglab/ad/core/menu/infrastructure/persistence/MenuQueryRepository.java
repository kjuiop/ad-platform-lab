package org.giglab.ad.core.menu.infrastructure.persistence;

import static org.giglab.ad.core.menu.domain.entity.QMenu.menu;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.global.jpa.entity.types.YnType;
import org.giglab.ad.core.menu.domain.entity.Menu;
import org.springframework.stereotype.Repository;

/** 메뉴 Querydsl 조회 레포지토리. */
@Repository
@RequiredArgsConstructor
public class MenuQueryRepository {

  private final JPAQueryFactory queryFactory;

  /** URL로 메뉴를 조회한다. */
  public Optional<Menu> findByUrl(String url) {
    return Optional.ofNullable(
        queryFactory.selectFrom(menu).where(defaultCondition(), menu.url.eq(url)).fetchOne());
  }

  /** ID로 메뉴를 조회한다. */
  public Optional<Menu> findById(Long id) {
    return Optional.ofNullable(
        queryFactory.selectFrom(menu).where(defaultCondition(), menu.id.eq(id)).fetchOne());
  }

  /** 활성 메뉴 수를 반환한다. */
  public long count() {
    Long result = queryFactory.select(menu.count()).from(menu).where(defaultCondition()).fetchOne();
    return result != null ? result : 0L;
  }

  private BooleanExpression defaultCondition() {
    return menu.deleteYn.eq(YnType.N);
  }
}
