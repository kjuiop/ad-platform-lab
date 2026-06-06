package org.giglab.ad.admin.api.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Transactional
class AuthControllerTest {

  @Autowired private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  @DisplayName("정상적인 회원가입 요청 시 201 Created 응답")
  void signup_success() throws Exception {
    String requestBody =
        "{"
            + "\"name\": \"홍길동\","
            + "\"email\": \"test@example.com\","
            + "\"password\": \"password123\","
            + "\"role\": \"ROLE_ADMINISTRATOR\""
            + "}";

    mockMvc
        .perform(
            post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.email").value("test@example.com"))
        .andExpect(jsonPath("$.name").value("홍길동"))
        .andExpect(jsonPath("$.role").value("ROLE_ADMINISTRATOR"))
        .andExpect(jsonPath("$.createdAt").exists());
  }

  @Test
  @DisplayName("이메일이 중복되면 409 Conflict 응답")
  void signup_duplicateEmail() throws Exception {
    String requestBody =
        "{"
            + "\"name\": \"테스트\","
            + "\"email\": \"dup@example.com\","
            + "\"password\": \"password123\","
            + "\"role\": \"ROLE_ADMINISTRATOR\""
            + "}";

    mockMvc
        .perform(
            post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isCreated());

    mockMvc
        .perform(
            post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.error.code").value("ADMIN-4201"))
        .andExpect(jsonPath("$.error.message").value("이미 등록된 이메일입니다."));
  }

  @Test
  @DisplayName("이메일 형식이 잘못되면 400 Bad Request 응답")
  void signup_invalidEmail() throws Exception {
    String requestBody =
        """
        {"name": "테스트", "email": "invalid-email", "password": "password123"}
        """;

    mockMvc
        .perform(
            post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("필수 필드가 누락되면 400 Bad Request 응답")
  void signup_missingFields() throws Exception {
    String requestBody =
        """
        {"name": "", "email": "", "password": ""}
        """;

    mockMvc
        .perform(
            post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("비밀번호가 8자 미만이면 400 Bad Request 응답")
  void signup_shortPassword() throws Exception {
    String requestBody =
        """
        {"name": "테스트", "email": "short@example.com", "password": "1234567"}
        """;

    mockMvc
        .perform(
            post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("올바른 이메일, 비밀번호로 로그인하면 200 OK 와 token 을 반환한다.")
  void login_success() throws Exception {
    // 먼저 회원가입
    mockMvc.perform(
        post("/api/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {"name": "홍길동", "email": "login@example.com",
                 "password": "password123", "role": "ROLE_ADMINISTRATOR"}
                """));

    // 로그인
    mockMvc
        .perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"email": "login@example.com", "password": "password123"}
                    """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").isString())
        .andExpect(jsonPath("$.role").value("ROLE_ADMINISTRATOR"));
  }

  @Test
  @DisplayName("존재하지 않는 이메일로 로그인하면 401 Unauthorized 응답")
  void login_invalidEmail() throws Exception {
    mockMvc
        .perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"email": "nobody@example.com", "password": "password123"}
                    """))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.error.code").value("ADMIN-4101"));
  }

  @Test
  @DisplayName("비밀번호가 틀리면 401 Unauthorized 응답")
  void login_wrongPassword() throws Exception {
    mockMvc.perform(
        post("/api/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {"name": "홍길동", "email": "wrong@example.com",
                 "password": "password123", "role": "ROLE_ADMINISTRATOR"}
                """));

    mockMvc
        .perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"email": "wrong@example.com", "password": "wrongpassword"}
                    """))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.error.code").value("ADMIN-4101"));
  }

  @Test
  @DisplayName("이메일 또는 비밀번호가 없으면 400 Bad Request 응답")
  void login_missingFields() throws Exception {
    mockMvc
        .perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"email": ""}
                    """))
        .andExpect(status().isBadRequest());
  }
}
