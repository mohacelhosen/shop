package com.store.shop.util;


public abstract class ApiResponse {

    public static <T> Success<T> success(String timestamp, String requestId, T data, String endpoint, Integer status) {
        return new Success<T>(timestamp, requestId, data, endpoint, status);
    }

    public static Error error(String timestamp, String requestId, String endpoint, Integer status, String error) {
        return new Error(timestamp, requestId, endpoint, status, error);
    }
}
