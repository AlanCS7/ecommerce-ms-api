package dev.alancss.ecommerce.handler;

import dev.alancss.ecommerce.exception.BusinessException;
import dev.alancss.ecommerce.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final HttpStatus NOT_FOUND_STATUS = HttpStatus.NOT_FOUND;
    private static final HttpStatus BAD_REQUEST_STATUS = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, ServletWebRequest request) {
        return buildErrorResponse(
                NOT_FOUND_STATUS,
                ex.getMessage(),
                request.getRequest().getRequestURI(),
                null
        );
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex, ServletWebRequest request) {
        return buildErrorResponse(
                BAD_REQUEST_STATUS,
                ex.getMessage(),
                request.getRequest().getRequestURI(),
                null
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, ServletWebRequest request) {
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return buildErrorResponse(
                BAD_REQUEST_STATUS,
                "Validation failed",
                request.getRequest().getRequestURI(),
                errors
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, ServletWebRequest request) {
        return buildErrorResponse(
                BAD_REQUEST_STATUS,
                "Malformed JSON request",
                request.getRequest().getRequestURI(),
                null
        );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status, String message, String path, Map<String, String> errors) {
        var errorResponse = new ErrorResponse(
                status.value(),
                message,
                OffsetDateTime.now(),
                path,
                errors
        );
        return ResponseEntity.status(status).body(errorResponse);
    }
}
