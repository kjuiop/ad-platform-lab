package org.giglab.ad.admin.api.utils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class JwtProviderTest {

  private JwtProvider jwtProvider;

  @BeforeEach
  void setUp() {
    jwtProvider = new JwtProvider();
    ReflectionTestUtils.setField(
        jwtProvider, "secret", "test-secret-key-must-be-at-least-32-chars!!");
    ReflectionTestUtils.setField(jwtProvider, "expirationMs", 3600000L);
  }

  @Test
  @DisplayName("generate 후 parse 하면 동일한 adminId 와 role 을 반환한다.")
  void generateAndParse() {
    String token = jwtProvider.generateToken(1L, "ROLE_ADMINISTRATOR");

    Claims claims = jwtProvider.parse(token);

    assertThat(claims.getSubject()).isEqualTo("1");
    assertThat(claims.get("role")).isEqualTo("ROLE_ADMINISTRATOR");
  }
}
