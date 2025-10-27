package com.gatiya.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper<T> {

    // Indicated if the request was successful or not.
    private Boolean success;

    // HTTP status code
    private Integer statusCode;

    // Human-readable message
    private String message;

    // Response data
    private T data;

    // Request timestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    // Request path
    private String path;

    // Helper method to get current request path
    private static String getCurrentRequestPath() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null)
            return attributes.getRequest().getRequestURI();
        return null;
    }

    // *************** Success Response Builder ***************
    // Success response (200) with data
    public static <T> ResponseWrapper<T> success(T data) {
        return ResponseWrapper.<T>builder()
                .success(true)
                .statusCode(HttpStatus.OK.value()) // 200
                .data(data)
                .timestamp(LocalDateTime.now())
                .path(getCurrentRequestPath())
                .build();
    }

    // Success response (200) with message and data
    public static <T>ResponseWrapper<T> success(String message, T data) {
        return ResponseWrapper.<T>builder()
                .success(true)
                .statusCode(HttpStatus.OK.value()) // 200
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .path(getCurrentRequestPath())
                .build();
    }

    // Created response (201)
    public static <T> ResponseWrapper<T> created(String message, T data) {
        return ResponseWrapper.<T>builder()
                .success(true)
                .statusCode(HttpStatus.CREATED.value()) // 201
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .path(getCurrentRequestPath())
                .build();
    }

    // No content response (204) - for successful DELETE operations
    public static <T> ResponseWrapper<T> noContent(String message) {
        return ResponseWrapper.<T>builder()
                .success(true)
                .statusCode(HttpStatus.NO_CONTENT.value()) // 204
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(getCurrentRequestPath())
                .build();
    }

    // *************** Error Response Builder ***************
    // Generic Response (500) with message
    public static <T> ResponseWrapper<T> error(String message) {
        return ResponseWrapper.<T>builder()
                .success(false)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()) // 500
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(getCurrentRequestPath())
                .build();
    }

    // Generic Response with message and status code
    public static <T> ResponseWrapper<T> error(String message, Integer statusCode) {
        return ResponseWrapper.<T>builder()
                .success(false)
                .statusCode(statusCode)
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(getCurrentRequestPath())
                .build();
    }
}
