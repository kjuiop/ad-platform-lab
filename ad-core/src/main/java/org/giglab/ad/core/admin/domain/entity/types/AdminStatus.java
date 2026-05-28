package org.giglab.ad.core.admin.domain.entity.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminStatus {
  PENDING("Pending", "대기"),

  NORMAL("Normal", "활성"),

  WITHDRAW("Withdraw", "탈퇴"),

  INACTIVE("InActive", "비활성화");

  private final String key;

  private final String description;
}
