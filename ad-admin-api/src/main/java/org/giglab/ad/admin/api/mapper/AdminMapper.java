package org.giglab.ad.admin.api.mapper;

import org.giglab.ad.admin.api.dto.SignupRequest;
import org.giglab.ad.admin.api.dto.SignupResponse;
import org.giglab.ad.core.admin.application.dto.command.CreateAdminCommand;
import org.giglab.ad.core.admin.application.dto.command.CreateAdminResult;
import org.mapstruct.Mapper;

/** 관리자 API DTO ↔ 코어 커맨드·결과 간 매핑 인터페이스. */
@Mapper(componentModel = "spring")
public interface AdminMapper {

  /** SignupRequest를 CreateAdminCommand로 변환한다. */
  CreateAdminCommand toCreateAdminCommand(SignupRequest request);

  /** CreateAdminResult를 SignupResponse로 변환한다. */
  SignupResponse toSignupResponse(CreateAdminResult result);
}
