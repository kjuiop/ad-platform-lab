package org.giglab.ad.core.admin.application.dto.command;

import java.time.LocalDateTime;

public record CreateAdminResult(
    Long id, String email, String name, String role, LocalDateTime createdAt) {}
