package org.giglab.ad.admin.api.response;

import lombok.Getter;

/** API 응답 래퍼 클래스. */
@Getter
public class ApiResponse<T> {

  private final T data;
  private final ErrorResponse error;

  private ApiResponse(T data, ErrorResponse error) {
    this.data = data;
    this.error = error;
  }

  /** 데이터가 있는 성공 응답을 생성한다. */
  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>(data, null);
  }

  /** 데이터가 없는 성공 응답을 생성한다. */
  public static ApiResponse<Void> success() {
    return new ApiResponse<>(null, null);
  }

  /** 에러 응답을 생성한다. */
  public static ApiResponse<Void> error(String code, String message) {
    return new ApiResponse<>(null, new ErrorResponse(code, message));
  }

  /** 에러 응답 내부 클래스. */
  @Getter
  public static class ErrorResponse {
    private final String code;
    private final String message;

    /** 에러 코드와 메시지로 에러 응답을 생성한다. */
    public ErrorResponse(String code, String message) {
      this.code = code;
      this.message = message;
    }
  }
}
