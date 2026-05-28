package org.giglab.ad.admin.api.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

  private final T data;
  private final ErrorResponse error;

  private ApiResponse(T data, ErrorResponse error) {
    this.data = data;
    this.error = error;
  }

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>(data, null);
  }

  public static ApiResponse<Void> success() {
    return new ApiResponse<>(null, null);
  }

  public static ApiResponse<Void> error(String code, String message) {
    return new ApiResponse<>(null, new ErrorResponse(code, message));
  }

  @Getter
  public static class ErrorResponse {
    private final String code;
    private final String message;

    public ErrorResponse(String code, String message) {
      this.code = code;
      this.message = message;
    }
  }
}
