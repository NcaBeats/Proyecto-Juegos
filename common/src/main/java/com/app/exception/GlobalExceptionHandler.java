package com.app.exception;

import com.app.exception.custom.ErrorResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
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

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> EntityNotFound(EntityNotFoundException ex , HttpServletRequest request) {
        log.warn("Entidad no encontrada; {} - en la ruta: {} - IP: {}",
                ex.getMessage(),
                request.getRequestURI(),
                request.getRemoteAddr());
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                404,
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponse> EntityExists(EntityExistsException ex , HttpServletRequest request) {
        log.warn("Entidad ya existente; {} - en la ruta: {} - IP: {}",
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

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
        log.warn("Estado inválido; {} - en la ruta: {} - IP: {}",
                ex.getMessage(), request.getRequestURI(), request.getRemoteAddr());
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                409,
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler({ NumberFormatException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class })
    public ResponseEntity<ErrorResponse> BadRequest(Exception ex, HttpServletRequest request) {
        log.warn("Solicitud inválida; {} - en la ruta: {} - IP: {}",
                ex.getMessage(),
                request.getRequestURI(),
                request.getRemoteAddr());
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                400,
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> ValidationFailed(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.warn("Validación fallida; {} - en la ruta: {} - IP: {}",
                ex.getMessage(),
                request.getRequestURI(),
                request.getRemoteAddr());
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                400,
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DataIntegrityViolationException ex, HttpServletRequest request) {
        log.warn("Conflicto de datos; {} - en la ruta: {} - IP: {}",
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
