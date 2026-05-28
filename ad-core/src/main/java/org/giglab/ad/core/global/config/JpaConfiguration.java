package org.giglab.ad.core.global.config;

import java.util.Optional;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** JPA 설정 클래스. */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaRepositories(basePackages = "org.giglab.ad.core")
@EntityScan(basePackages = "org.giglab.ad.core")
public class JpaConfiguration {

  /** Auditor 프로바이더 빈을 등록한다. */
  @Bean
  public AuditorAware<Long> auditorProvider() {
    // 로그인 구현 후 SecurityContext에서 현재 사용자 ID 반환으로 교체
    return Optional::empty;
  }
}
