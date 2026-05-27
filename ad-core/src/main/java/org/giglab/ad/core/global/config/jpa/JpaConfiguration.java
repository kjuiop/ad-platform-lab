package org.giglab.ad.core.global.config.jpa;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "org.giglab.ad.core")
@EntityScan(basePackages = "org.giglab.ad.core")
public class JpaConfiguration {}
