package org.giglab.ad.core.admin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.giglab.ad.core.admin.domain.entity.Admin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Admin 엔티티 단위 테스트")
class AdminTest {

  @Test
  @DisplayName("정적 팩터리 메서드로 Admin을 생성하면 전달한 값이 그대로 저장된다")
  void create_setsFieldsCorrectly() {
    Admin admin = Admin.create("admin@example.com", "홍길동", "encodedPw", "ROLE_ADMINISTRATOR");

    assertThat(admin.getEmail()).isEqualTo("admin@example.com");
    assertThat(admin.getName()).isEqualTo("홍길동");
    assertThat(admin.getPassword()).isEqualTo("encodedPw");
  }

  @Test
  @DisplayName("새로 생성된 Admin의 id는 null이다 (영속화 전)")
  void create_idIsNullBeforePersist() {
    Admin admin = Admin.create("admin@example.com", "홍길동", "encodedPw", "ROLE_ADMINISTRATOR");

    assertThat(admin.getId()).isNull();
  }

  @Test
  @DisplayName("새로 생성된 Admin의 roles는 비어 있다")
  void create_rolesIsEmptyBeforeAddRole() {
    Admin admin = Admin.create("admin@example.com", "홍길동", "encodedPw", "ROLE_ADMINISTRATOR");

    assertThat(admin.getRoles()).isEmpty();
  }

  @Test
  @DisplayName("addRole로 역할을 추가하면 roles 목록에 포함된다")
  void addRole_addsToRolesList() {
    Admin admin = Admin.create("admin@example.com", "홍길동", "encodedPw", "ROLE_ADMINISTRATOR");

    admin.addRole("ROLE_ADMINISTRATOR");

    assertThat(admin.getRoles()).hasSize(1);
    assertThat(admin.getRoles().getFirst().getRole()).isEqualTo("ROLE_ADMINISTRATOR");
  }
}
