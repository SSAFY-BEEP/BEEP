package com.example.Beep.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /*
     * Developer Custom Exception
     */
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<?> handleCustomException(final CustomException e) {
        log.error("handleCustomException: {}", e.getErrorCode());
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getErrorCode()), HttpStatus.MULTI_STATUS);
    }

    /*
     * HTTP 405 Exception
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<?> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException: {}", e.getMessage());
        return new ResponseEntity<ErrorResponse>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    /*
     * HTTP 500 Exception
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(final Exception e) {
        log.error("handleException: {}", e.getMessage());
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}