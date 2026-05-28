package org.giglab.ad.core.global.jpa.entity;

import java.time.LocalDateTime;

/** 기본 엔티티 인터페이스. */
public interface BaseEntity {

  /** 생성 일시를 반환한다. */
  LocalDateTime getCreatedAt();

  /** 수정 일시를 반환한다. */
  LocalDateTime getUpdatedAt();

  /** 생성자 ID를 반환한다. */
  Long getCreatedBy();

  /** 수정자 ID를 반환한다. */
  Long getUpdatedBy();
}
