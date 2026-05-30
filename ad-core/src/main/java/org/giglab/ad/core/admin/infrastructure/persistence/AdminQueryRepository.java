package org.giglab.ad.core.admin.infrastructure.persistence;

import static org.giglab.ad.core.admin.domain.entity.QAdmin.admin;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.global.jpa.entity.types.YnType;
import org.springframework.stereotype.Repository;

/** 관리자 Querydsl 조회 레포지토리. */
@Repository
@RequiredArgsConstructor
public class AdminQueryRepository {

  private final JPAQueryFactory queryFactory;

  /** 이메일로 관리자 존재 여부를 반환한다. */
  public boolean existsByEmail(String email) {
    return queryFactory
            .selectOne()
            .from(admin)
            .where(defaultCondition(), admin.email.eq(email))
            .fetchFirst()
        != null;
  }

  /** 활성 관리자 수를 반환한다. */
  public long count() {
    Long result =
        queryFactory.select(admin.count()).from(admin).where(defaultCondition()).fetchOne();
    return result != null ? result : 0L;
  }

  private BooleanExpression defaultCondition() {
    return admin.deleteYn.eq(YnType.N);
  }
}
