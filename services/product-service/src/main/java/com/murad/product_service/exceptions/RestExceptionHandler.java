package com.murad.product_service.exceptions;

import com.murad.product_service.dto.ErrorDetailsResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * Handles MethodArgumentNotValidException by extracting field errors from the binding result and returning an
     * ErrorDetailsResponseMessage with the appropriate HTTP status code and error message.
     *
     * @param  e   the MethodArgumentNotValidException to handle
     * @return     an ErrorDetailsResponse with the appropriate HTTP status code, error message, and list of field errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetailsResponse<List<String>> handleValidationException(MethodArgumentNotValidException e) {
        var errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> String.format("%s::%s", fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        return new ErrorDetailsResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Validation error",
                errors);
    }

    /**
     * Handles DataIntegrityViolationException
     * by extracting the detail message from the cause and returning an ErrorDetailsResponse
     * with the appropriate HTTP status code and error message.
     *
     * @param  e   the DataIntegrityViolationException to handle
     * @return     an ErrorDetailsResponse with the appropriate HTTP status code, error message, and detail message
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetailsResponse<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        var detail = e.getMostSpecificCause().getMessage();
        detail = detail.contains("Detail:") ? detail.split("Detail: ")[1] : detail; // get only specific detail

        return new ErrorDetailsResponse<>(
                HttpStatus.CONFLICT.value(),
                "Record already exists",
                detail);
    }
}
