package org.giglab.ad.core.admin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.giglab.ad.core.admin.domain.entity.Admin;
import org.giglab.ad.core.admin.domain.entity.AdminRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Admin 엔티티 단위 테스트")
class AdminTest {

  @Test
  @DisplayName("정적 팩터리 메서드로 Admin을 생성하면 전달한 값이 그대로 저장된다")
  void create_setsFieldsCorrectly() {
    Admin admin = Admin.create("admin@example.com", "홍길동", "encodedPw", AdminRole.ADMINISTRATOR);

    assertThat(admin.getEmail()).isEqualTo("admin@example.com");
    assertThat(admin.getName()).isEqualTo("홍길동");
    assertThat(admin.getPassword()).isEqualTo("encodedPw");
    assertThat(admin.getRole()).isEqualTo(AdminRole.ADMINISTRATOR);
  }

  @Test
  @DisplayName("새로 생성된 Admin의 id는 null이다 (영속화 전)")
  void create_idIsNullBeforePersist() {
    Admin admin = Admin.create("admin@example.com", "홍길동", "encodedPw", AdminRole.ADMINISTRATOR);

    assertThat(admin.getId()).isNull();
  }

  @Test
  @DisplayName("PARTNER_ADMIN 역할로 Admin을 생성할 수 있다")
  void create_withPartnerAdminRole() {
    Admin admin = Admin.create("partner@example.com", "파트너", "encodedPw", AdminRole.PARTNER_ADMIN);

    assertThat(admin.getRole()).isEqualTo(AdminRole.PARTNER_ADMIN);
  }

  @Test
  @DisplayName("BRAND_ADMIN 역할로 Admin을 생성할 수 있다")
  void create_withBrandAdminRole() {
    Admin admin = Admin.create("brand@example.com", "브랜드", "encodedPw", AdminRole.BRAND_ADMIN);

    assertThat(admin.getRole()).isEqualTo(AdminRole.BRAND_ADMIN);
  }
}
