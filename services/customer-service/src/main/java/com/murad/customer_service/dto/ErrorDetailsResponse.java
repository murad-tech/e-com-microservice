package com.murad.customer_service.dto;

public record ErrorDetailsResponse<T>(int status, String errorType, T message) {
}
