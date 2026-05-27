package org.giglab.ad.admin.api.dto;

import java.time.LocalDateTime;

/** 회원가입 응답 DTO. */
public record SignupResponse(
    Long id, String email, String name, String role, LocalDateTime createdAt) {}
