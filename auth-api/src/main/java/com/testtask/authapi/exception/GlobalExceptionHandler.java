package com.testtask.authapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {
    private final HttpServletRequest httpServletRequest;

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleException(ConflictException e) {

        ApiError error = new ApiError(
                httpServletRequest.getRequestURI(),
                e.getMessage(),
                CONFLICT.value(),
                Instant.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleException(UnauthorizedException e) {

        ApiError error = new ApiError(
                httpServletRequest.getRequestURI(),
                e.getMessage(),
                UNAUTHORIZED.value(),
                Instant.now()
        );

        return ResponseEntity.status(UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleException(NotFoundException e) {

        ApiError error = new ApiError(
                httpServletRequest.getRequestURI(),
                e.getMessage(),
                NOT_FOUND.value(),
                Instant.now()
        );

        return ResponseEntity.status(NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e) {

        ApiError error = new ApiError(
                httpServletRequest.getRequestURI(),
                e.getMessage(),
                INTERNAL_SERVER_ERROR.value(),
                Instant.now()
        );

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(error);
    }
}
