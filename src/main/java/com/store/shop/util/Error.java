package com.store.shop.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Error {
    private String timestamp;
    private String requestId;
    private final String data = null;
    private String endpoint;
    private Integer status;
    private String error;

    public Error(String timestamp, String requestId, String endpoint, Integer status, String error) {
        this.timestamp = timestamp;
        this.requestId = requestId;
        this.endpoint = endpoint;
        this.status = status;
        this.error = error;
    }
}
