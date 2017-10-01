package com.company.app.server.web.handlers;

import com.company.app.client.web.dtos.ErrorCodes;
import com.company.app.client.web.dtos.errors.ErrorDto;
import com.company.app.client.web.dtos.errors.HttpMediaTypeErrorDto;
import com.company.app.client.web.dtos.errors.HttpRequestMethodErrorDto;
import com.github.rozidan.springboot.logger.Loggable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

/**
 * @author Idan Rozenfeld
 */
@Slf4j
@RestControllerAdvice
public class GlobalErrorHandlers {

    @Loggable
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorDto<HttpRequestMethodErrorDto> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return new ErrorDto.ErrorDtoBuilder<HttpRequestMethodErrorDto>()
                .errorCode(ErrorCodes.METHOD_NOT_ALLOWED)
                .errors(Collections.singleton(HttpRequestMethodErrorDto.builder()
                        .actualMethod(ex.getMethod())
                        .supportedMethods(ex.getSupportedMethods())
                        .build()))
                .build();
    }


    @Loggable
    @ResponseStatus(code = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ErrorDto<HttpMediaTypeErrorDto> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        return new ErrorDto.ErrorDtoBuilder<HttpMediaTypeErrorDto>()
                .errorCode(ErrorCodes.HTTP_MEDIA_TYPE_NOT_SUPPORTED)
                .errors(Collections.singleton(HttpMediaTypeErrorDto.builder()
                        .mediaType(ex.getContentType().toString())
                        .build()))
                .build();
    }


    @Loggable(LogLevel.ERROR)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDto<Void> handleGlobalError(Exception ex) {
        log.error("Global error handler exception: ", ex);
        return new ErrorDto.ErrorDtoBuilder<Void>()
                .errorCode(ErrorCodes.UNKNOWN)
                .build();
    }
}