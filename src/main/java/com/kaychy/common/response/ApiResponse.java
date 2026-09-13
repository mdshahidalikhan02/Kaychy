package com.kaychy.common.response;

public record ApiResponse<T>(T data, String message) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, "Success");
    }
}
