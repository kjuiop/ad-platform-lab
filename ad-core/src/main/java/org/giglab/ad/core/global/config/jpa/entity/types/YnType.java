package org.giglab.ad.core.global.config.jpa.entity.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YnType {
  Y("Y", "Y"),

  N("N", "N");

  private final String key;

  private final String description;
}
