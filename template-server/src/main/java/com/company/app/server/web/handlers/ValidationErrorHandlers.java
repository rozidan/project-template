package com.company.app.server.web.handlers;

import com.company.app.client.web.dtos.ErrorCodes;
import com.company.app.client.web.dtos.errors.ErrorDto;
import com.company.app.client.web.dtos.errors.ValidationErrorDto;
import com.github.rozidan.springboot.logger.Loggable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Idan Rozenfeld
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ValidationErrorHandlers {

    @Loggable
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorDto<Void> handleNotReadableError(HttpMessageNotReadableException ex) {
        return new ErrorDto.ErrorDtoBuilder<Void>().errorCode(ErrorCodes.REQUEST_NOT_READABLE).build();
    }

    @Loggable
    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto<ValidationErrorDto> handleValidationError(MethodArgumentNotValidException ex) {
        Set<ValidationErrorDto> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> ValidationErrorDto.builder()
                        .errorCode(err.getCode())
                        .fieldName(err.getField())
                        .rejectedValue(err.getRejectedValue())
                        .params(Stream.of(err.getArguments())
                                .skip(1)
                                .map(Object::toString)
                                .collect(Collectors.toList())
                                .toArray())
                        .build())
                .collect(Collectors.toSet());

        return new ErrorDto.ErrorDtoBuilder<ValidationErrorDto>()
                .errorCode(ErrorCodes.DATA_VALIDATION)
                .errors(errors)
                .build();
    }
}