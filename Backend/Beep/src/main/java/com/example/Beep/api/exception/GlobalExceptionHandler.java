package com.example.Beep.api.exception;

import com.example.Beep.api.domain.dto.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /*
     * Developer Custom Exception
     */
    @ExceptionHandler(CustomException.class)
    protected ApiResult<?> handleCustomException(final CustomException e) {
        log.error("handleCustomException: {}", e.getErrorCode());
        return new ApiResult<ErrorResponse>(new ErrorResponse(e.getErrorCode()), HttpStatus.MULTI_STATUS);
    }
}