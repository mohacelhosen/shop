package com.store.shop.exception;

import com.store.shop.util.ApiResponse;
import com.store.shop.util.Common;
import com.store.shop.util.Error;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Error> handleDuplicateKeyException(DuplicateKeyException ex, HttpServletRequest request) {
        String timestamp = Common.getTimeStamp();
        String requestId = Common.getRequestId();
        String endpoint = request.getRequestURI();

        Error errorResponse = ApiResponse.error(
                timestamp,
                requestId,
                endpoint,
                HttpStatus.CONFLICT.value(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
