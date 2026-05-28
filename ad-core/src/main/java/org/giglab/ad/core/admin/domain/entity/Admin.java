package org.giglab.ad.core.admin.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.giglab.ad.core.global.jpa.entity.AuditedEntity;
import org.giglab.ad.core.global.jpa.entity.types.YnType;

@Getter
@Builder
@Entity
@Table(name = "administrators")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends AuditedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Builder.Default
  @Column(columnDefinition = "varchar(2) default 'N'", nullable = false)
  @Enumerated(EnumType.STRING)
  private YnType deleteYn = YnType.N;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AdminRole role;

  public static Admin create(String email, String name, String password, AdminRole role) {
    return Admin.builder().email(email).name(name).password(password).role(role).build();
  }
}
