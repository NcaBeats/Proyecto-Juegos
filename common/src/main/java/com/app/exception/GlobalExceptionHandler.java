package com.app.exception;

import com.app.exception.custom.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> IllegalArgument(IllegalArgumentException ex ,HttpServletRequest request) {
        log.warn("Conflicto de entidad; {} - en la ruta: {} - IP: {}",
                ex.getMessage(),
                request.getRequestURI(),
                request.getRemoteAddr());
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                409,
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
