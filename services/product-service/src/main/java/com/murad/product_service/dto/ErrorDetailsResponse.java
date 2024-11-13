package com.murad.product_service.dto;

public record ErrorDetailsResponse<T>(int status, String errorType, T message) {
}
