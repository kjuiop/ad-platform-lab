package org.giglab.ad.core.global.jpa.entity.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Y/N 타입 열거형. */
@Getter
@AllArgsConstructor
public enum YnType {
  Y("Y", "Y"),

  N("N", "N");

  private final String key;

  private final String description;
}
