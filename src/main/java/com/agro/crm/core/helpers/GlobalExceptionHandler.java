package com.agro.crm.core.helpers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(EntityNotFoundException ex,
                                   HttpServletRequest req) {
        log.warn("Not found: {}", ex.getMessage());
        return ApiError.builder()
                .status(404)
                .error("Not Found")
                .message(ex.getMessage())
                .path(req.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIllegalArgument(IllegalArgumentException ex,
                                          HttpServletRequest req) {
        log.warn("Bad request: {}", ex.getMessage());
        return ApiError.builder()
                .status(400)
                .error("Bad Request")
                .message(ex.getMessage())
                .path(req.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleIllegalState(IllegalStateException ex,
                                       HttpServletRequest req) {
        log.warn("Conflict: {}", ex.getMessage());
        return ApiError.builder()
                .status(409)
                .error("Conflict")
                .message(ex.getMessage())
                .path(req.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiError handleValidation(MethodArgumentNotValidException ex,
                                     HttpServletRequest req) {
        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fe -> fe.getDefaultMessage() != null
                                ? fe.getDefaultMessage()
                                : "Invalid value",
                        (a, b) -> a
                ));

        return ApiError.builder()
                .status(422)
                .error("Validation Failed")
                .message("Request validation failed")
                .path(req.getRequestURI())
                .timestamp(LocalDateTime.now())
                .fieldErrors(fieldErrors)
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleAccessDenied(AccessDeniedException ex,
                                       HttpServletRequest req) {
        log.warn("Access denied: {} {}", req.getMethod(), req.getRequestURI());
        return ApiError.builder()
                .status(403)
                .error("Forbidden")
                .message("You don't have permission to access this resource")
                .path(req.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleAll(Exception ex, HttpServletRequest req) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return ApiError.builder()
                .status(500)
                .error("Internal Server Error")
                .message("Something went wrong. Please try again later.")
                .path(req.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
