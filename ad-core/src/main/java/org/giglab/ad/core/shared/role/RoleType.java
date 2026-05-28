package org.giglab.ad.core.shared.role;

/** 시스템에서 지원하는 역할 타입. */
public enum RoleType {
  ROLE_ADMINISTRATOR,
  ROLE_PARTNER_ADMIN,
  ROLE_BRAND_ADMIN;

  /** 유효한 역할 문자열인지 반환한다. */
  public static boolean isValid(String value) {
    for (RoleType type : values()) {
      if (type.name().equals(value)) {
        return true;
      }
    }
    return false;
  }
}
