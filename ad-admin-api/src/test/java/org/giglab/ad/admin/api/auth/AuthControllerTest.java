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
        """
        {"name": "홍길동", "email": "test@example.com", "password": "password123"}
        """;

    mockMvc
        .perform(
            post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.email").value("test@example.com"))
        .andExpect(jsonPath("$.name").value("홍길동"))
        .andExpect(jsonPath("$.role").value("ADMINISTRATOR"))
        .andExpect(jsonPath("$.createdAt").exists());
  }

  @Test
  @DisplayName("이메일이 중복되면 409 Conflict 응답")
  void signup_duplicateEmail() throws Exception {
    String requestBody =
        """
        {"name": "테스트", "email": "dup@example.com", "password": "password123"}
        """;

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
}
