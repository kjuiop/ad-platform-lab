package org.giglab.ad.core.global.config.jpa.entity;

import java.time.LocalDateTime;

public interface BaseEntity {

  LocalDateTime getCreatedAt();

  LocalDateTime getUpdatedAt();

  Long getCreatedBy();

  Long getUpdatedBy();
}
