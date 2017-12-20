package com.company.template.server.web.handlers;

import com.company.template.client.web.dtos.ErrorCodes;
import com.company.template.client.web.dtos.errors.ErrorDto;
import com.company.template.client.web.dtos.errors.ValidationErrorDto;
import com.company.template.server.domain.model.constraints.DataUniqueConstraint;
import com.github.rozidan.springboot.logger.Loggable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Idan Rozenfeld
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class DataExceptionHandlers {

    private final List<DataUniqueConstraint> uniqueConstraintList;

    @Autowired(required = false)
    public DataExceptionHandlers(List<DataUniqueConstraint> uniqueConstraintList) {
        this.uniqueConstraintList = uniqueConstraintList;
    }

    @Loggable
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ErrorDto handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        return ErrorDto.builder()
                .errorCode(ErrorCodes.NOT_FOUND)
                .message(ex.getLocalizedMessage())
                .build();
    }

    @Loggable
    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorDto handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        if (uniqueConstraintList != null && ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) {
            Optional<DataUniqueConstraint> matchCons = uniqueConstraintList.stream().filter((cons) ->
                    ((ConstraintViolationException) ex.getCause()).getConstraintName().contains(cons.getConstraintName())).findFirst();
            if (matchCons.isPresent()) {
                return ErrorDto.builder()
                        .errorCode(ErrorCodes.DATA_VALIDATION)
                        .errors(Arrays.stream(matchCons.get().getFieldNames())
                                .map(field -> ValidationErrorDto.builder()
                                        .errorCode("UNIQUE")
                                        .fieldName(field)
                                        .build())
                                .collect(Collectors.toSet()))
                        .message(ex.getLocalizedMessage())
                        .build();
            }
        }

        return ErrorDto.builder()
                .errorCode(ErrorCodes.UNKNOWN)
                .message(ex.getLocalizedMessage())
                .build();
    }

}
