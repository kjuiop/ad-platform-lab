package org.giglab.ad.core.admin.application.dto.command;

import java.time.LocalDateTime;

/** 관리자 생성 결과. */
public record CreateAdminResult(
    Long id, String email, String name, String role, LocalDateTime createdAt) {}
