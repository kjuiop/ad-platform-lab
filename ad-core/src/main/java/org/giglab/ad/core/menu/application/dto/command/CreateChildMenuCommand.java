package org.giglab.ad.core.menu.application.dto.command;

import java.util.List;

/** 자식 메뉴 생성 커맨드. */
public record CreateChildMenuCommand(
    String name, String url, int sortOrder, List<String> roles, Long parentId) {}
