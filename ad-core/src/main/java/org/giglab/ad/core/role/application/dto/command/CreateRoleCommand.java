package org.giglab.ad.core.role.application.dto.command;

/** 역할 생성 커맨드. */
public record CreateRoleCommand(String name, String description, int sortOrder) {}
