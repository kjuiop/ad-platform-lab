package org.giglab.ad.admin.api.controller.init;

import lombok.RequiredArgsConstructor;
import org.giglab.ad.admin.api.init.InitDataUtils;
import org.giglab.ad.admin.api.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 초기 데이터 생성 API 컨트롤러. */
@RestController
@RequestMapping("/api/init-data")
@RequiredArgsConstructor
public class InitController {

  private final InitDataUtils initDataUtils;

  /** 초기 데이터를 생성한다. */
  @PostMapping
  public ResponseEntity<ApiResponse<Void>> init() {
    initDataUtils.init();
    return ResponseEntity.ok(ApiResponse.success());
  }
}
