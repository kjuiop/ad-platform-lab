package org.giglab.ad.core.menu.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.giglab.ad.core.global.jpa.entity.AuditedEntity;
import org.giglab.ad.core.global.jpa.entity.types.YnType;

/** 메뉴 엔티티. */
@Entity
@Builder
@Table(name = "menus")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Menu extends AuditedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String url;

  @Builder.Default
  @Column(columnDefinition = "varchar(2) default 'N'", nullable = false)
  @Enumerated(EnumType.STRING)
  private YnType deleteYn = YnType.N;

  @Builder.Default
  @Column(columnDefinition = "varchar(2) default 'Y'", nullable = false)
  @Enumerated(EnumType.STRING)
  private YnType activeYn = YnType.Y;

  @Builder.Default
  @Column(columnDefinition = "varchar(2) default 'Y'", nullable = false)
  @Enumerated(EnumType.STRING)
  private YnType displayYn = YnType.Y;

  private int sortOrder;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Menu parent;

  @Builder.Default
  @OneToMany(
      mappedBy = "parent",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @OrderBy("sortOrder ASC")
  private List<Menu> children = new ArrayList<>();

  @Builder.Default
  @OneToMany(
      mappedBy = "menu",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<MenuRole> menuRoles = new ArrayList<>();

  /** 주어진 역할이 이 메뉴에 접근 가능한지 확인한다. */
  public boolean isAccessibleBy(String roleName) {
    return menuRoles.stream().anyMatch(mr -> mr.getRole().equals(roleName));
  }

  /** 메뉴가 활성 상태이고 표시 가능한지 확인한다. */
  public boolean isVisible() {
    return displayYn == YnType.Y && activeYn == YnType.Y && deleteYn == YnType.N;
  }

  /** 메뉴를 생성한다. */
  public static Menu create(String name, String url, int sortOrder) {
    return Menu.builder().name(name).url(url).sortOrder(sortOrder).build();
  }

  /** 부모 메뉴를 연결한다. */
  public void attachParent(Menu parent) {
    this.parent = parent;
    parent.children.add(this);
  }

  /** 역할을 추가한다. */
  public void addRole(String roleName) {
    this.menuRoles.add(MenuRole.of(this, roleName));
  }
}
