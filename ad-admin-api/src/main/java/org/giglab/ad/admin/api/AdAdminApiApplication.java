package org.giglab.ad.admin.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** 광고 관리자 API 애플리케이션. */
@SpringBootApplication(scanBasePackages = {"org.giglab.ad"})
public class AdAdminApiApplication {

  /** 애플리케이션 진입점. */
  public static void main(String[] args) {
    SpringApplication.run(AdAdminApiApplication.class, args);
  }
}
