package org.giglab.ad.core.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/** 패스워드 인코더 설정 클래스. */
@Configuration
public class PasswordEncoderConfig {

  /** BCrypt 패스워드 인코더 빈을 등록한다. */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
