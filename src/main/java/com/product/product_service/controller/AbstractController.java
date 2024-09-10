package com.product.product_service.controller;

import com.product.product_service.exception.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
@RequestMapping("api/v1/products")
public abstract class AbstractController {

    protected <T> ResponseEntity<T> successResponse(T data, HttpStatus status) {
        return new ResponseEntity<>(data, status);

    }

    protected ResponseEntity<Void> noContentResponse() {
        return ResponseEntity.noContent().build();

    }
    protected ResponseEntity<ApiResponse> buildErrorResponse(String message, HttpStatus status, String details) {
        ApiResponse errorDetails = new ApiResponse(status.value(), message, details);
        return new ResponseEntity<>(errorDetails, status);
    }

    protected <T> ResponseEntity<T> createdResponse(T data){

        return new ResponseEntity<>(data,HttpStatus.CREATED);
    }

}
