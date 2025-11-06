package com.chatr.shared.exceptions.handler;

import com.chatr.shared.exceptions.BaseException;
import com.chatr.shared.utils.ErrorResponse;
import com.chatr.shared.exceptions.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    private ResponseEntity<ErrorResponse> handleBaseException(BaseException exception, WebRequest request) {
        logger.warn("=+=+=+=+=+=+Custom exception occurred=+=+=+=+=+=+: {}", exception.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(), exception.getMessage());
        setRequestDetails(errorResponse, request);

        return new ResponseEntity<>(errorResponse, exception.getHttpStatus());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception, WebRequest request) {
        logger.warn("=+=+=+=+=+=+User registration conflict=+=+=+=+=+=+: {}", exception.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(), exception.getMessage());
        setRequestDetails(errorResponse, request);

        return new ResponseEntity<>(errorResponse, exception.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException exception, WebRequest request) {
        logger.warn("=+=+=+=+=+=+Validation error=+=+=+=+=+=+: {}", exception.getMessage());

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR", errors);
        setRequestDetails(errorResponse, request);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    private void setRequestDetails(ErrorResponse errorResponse, WebRequest request) {
        if (request instanceof ServletWebRequest) {
            HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
            errorResponse.setPath(servletRequest.getRequestURI());
        }
    }
}