package org.giglab.ad.core.role.infrastructure.persistence;

import static org.giglab.ad.core.role.domain.entity.QRole.role;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.role.domain.entity.Role;
import org.springframework.stereotype.Repository;

/** 역할 Querydsl 조회 레포지토리. */
@Repository
@RequiredArgsConstructor
public class RoleQueryRepository {

  private final JPAQueryFactory queryFactory;

  /** 이름으로 역할을 조회한다. */
  public Optional<Role> findByName(String name) {
    return Optional.ofNullable(queryFactory.selectFrom(role).where(role.name.eq(name)).fetchOne());
  }

  /** 전체 역할 수를 반환한다. */
  public long count() {
    Long result = queryFactory.select(role.count()).from(role).fetchOne();
    return result != null ? result : 0L;
  }
}
