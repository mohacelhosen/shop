package com.store.shop.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Success<T> {
    private String timestamp;
    private String requestId;
    private T data;
    private String endpoint;
    private Integer status;
}
