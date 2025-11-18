package com.chatr.shared.exceptions.handler;

import com.chatr.shared.utils.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class EmailExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(EmailExceptionHandler.class);

    @ExceptionHandler(MailSendException.class)
    private ResponseEntity<ErrorResponse> handleMailSendException(MailSendException exception, WebRequest request) {
        logger.warn("EMAIL_SEND_ERROR: {}", exception.getMessage());

        ErrorResponse errorResponse = new ErrorResponse("EMAIL_SEND_ERROR", "Mail server connection failed.");
        GlobalExceptionHandler.setRequestDetails(errorResponse, request);

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponse);
    }
}
