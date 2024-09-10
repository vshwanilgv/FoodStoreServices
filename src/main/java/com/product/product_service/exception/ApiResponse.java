package com.product.product_service.exception;


import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse {
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;
    private final String details;

    public ApiResponse(int status, String message, String details) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.details = details;
    }


}