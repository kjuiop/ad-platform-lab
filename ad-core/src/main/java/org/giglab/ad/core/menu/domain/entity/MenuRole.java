package org.giglab.ad.core.menu.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.giglab.ad.core.global.jpa.entity.AuditedEntity;

/** 메뉴-역할 매핑 엔티티. */
@Builder
@Entity
@Table(
    name = "menu_roles",
    uniqueConstraints = @UniqueConstraint(columnNames = {"menu_id", "role"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuRole extends AuditedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_id", nullable = false)
  private Menu menu;

  @Column(nullable = false)
  private String role;

  /** 메뉴와 역할로 MenuRole을 생성한다. */
  public static MenuRole of(Menu menu, String role) {
    return MenuRole.builder().menu(menu).role(role).build();
  }
}
