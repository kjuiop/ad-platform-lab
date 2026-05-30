package org.giglab.ad.core.admin.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.giglab.ad.core.admin.domain.entity.types.AdminStatus;
import org.giglab.ad.core.global.jpa.entity.AuditedEntity;
import org.giglab.ad.core.global.jpa.entity.types.YnType;

/** 관리자 엔티티. */
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

  @Builder.Default
  @Column(length = 50)
  @Enumerated(EnumType.STRING)
  private AdminStatus status = AdminStatus.PENDING;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String password;

  @Builder.Default
  @OneToMany(
      mappedBy = "administrator",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<AdminRole> roles = new ArrayList<>();

  /** 관리자를 생성한다. */
  public static Admin create(String email, String name, String password) {
    return Admin.builder().email(email).name(name).password(password).build();
  }

  /** 역할을 추가한다. */
  public void addRole(String roleName) {
    this.roles.add(AdminRole.of(this, roleName));
  }
}
