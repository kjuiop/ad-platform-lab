package org.giglab.ad.core.role.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.giglab.ad.core.global.jpa.entity.AuditedEntity;
import org.giglab.ad.core.role.domain.exception.RoleDomainException;
import org.giglab.ad.core.role.domain.exception.RoleErrorCode;

@Entity
@Table(name = "roles")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Role extends AuditedEntity {

  private static final String rolePrefix = "ROLE_";

  @Id
  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private String description;

  @Builder.Default private int sortOrder = 0;

  public static Role createRole(String name, String description, int sortOrder) {
    if (!name.startsWith(rolePrefix)) {
      throw new RoleDomainException(RoleErrorCode.INVALID_ROLE_NAME);
    }
    return Role.builder().name(name).description(description).sortOrder(sortOrder).build();
  }
}
