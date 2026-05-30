package org.giglab.ad.admin.api.init;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.giglab.ad.core.admin.application.AdminService;
import org.giglab.ad.core.admin.application.dto.command.CreateAdminCommand;
import org.giglab.ad.core.menu.application.MenuService;
import org.giglab.ad.core.menu.application.dto.command.CreateChildMenuCommand;
import org.giglab.ad.core.menu.application.dto.command.CreateMenuCommand;
import org.giglab.ad.core.role.application.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 초기 데이터를 생성하는 서비스. */
@Service
@RequiredArgsConstructor
public class InitDataUtils {

  private final RoleService roleService;
  private final MenuService menuService;
  private final AdminService adminService;

  /** 역할, 메뉴, 관리자 초기 데이터를 생성한다. 이미 초기화된 경우 건너뛴다. */
  @Transactional
  public void init() {
    if (roleService.hasData() && menuService.hasData() && adminService.hasData()) {
      return;
    }
    initRoles();
    initMenus();
    initAdmins();
  }

  private void initRoles() {
    roleService.initRole("ROLE_ADMINISTRATOR", "시스템 관리자", 1);
    roleService.initRole("ROLE_PARTNER_ADMIN", "파트너 관리자", 2);
    roleService.initRole("ROLE_BRAND_ADMIN", "브랜드 관리자", 3);
  }

  private void initMenus() {
    List<String> allRoles = List.of("ROLE_ADMINISTRATOR", "ROLE_PARTNER_ADMIN", "ROLE_BRAND_ADMIN");
    List<String> adminOnly = List.of("ROLE_ADMINISTRATOR");

    menuService.createMenu(new CreateMenuCommand("대시보드", "/dashboard", 1, allRoles));
    menuService.createMenu(new CreateMenuCommand("광고 관리", "/ads", 2, allRoles));
    menuService.createMenu(new CreateMenuCommand("파트너 관리", "/partners", 3, adminOnly));

    Long settingsMenuId =
        menuService.createMenu(new CreateMenuCommand("설정", "/settings", 99, adminOnly));
    menuService.createChildMenu(
        new CreateChildMenuCommand("메뉴 관리", "/settings/menus", 1, adminOnly, settingsMenuId));
    menuService.createChildMenu(
        new CreateChildMenuCommand("관리자 관리", "/settings/admins", 2, adminOnly, settingsMenuId));
  }

  private void initAdmins() {
    adminService.createAdmin(
        new CreateAdminCommand("admin@giglab.org", "초기관리자", "admin1234!", "ROLE_ADMINISTRATOR"));
  }
}
