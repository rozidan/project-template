package com.company.template.client.web.dtos.errors;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.auditing.CurrentDateTimeProvider;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * @author Idan Rozenfeld
 */
@ApiModel("Error")
@Data
@EqualsAndHashCode(of = {"errorCode", "timestamp"})
public class ErrorDto<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(readOnly = true)
    private Enum<?> errorCode;

    @ApiModelProperty(readOnly = true)
    private Calendar timestamp;

    @ApiModelProperty(allowEmptyValue = true)
    private Set<T> errors;

    public static class ErrorDtoBuilder<T> {
        private Enum<?> errorCode;
        private Calendar timestamp;
        private Set<T> errors;

        public ErrorDtoBuilder() {
            timestamp = CurrentDateTimeProvider.INSTANCE.getNow();
        }

        public ErrorDtoBuilder<T> errorCode(Enum<?> errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public ErrorDtoBuilder<T> timestamp(Calendar timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorDtoBuilder<T> errors(Set<T> errors) {
            this.errors = errors;
            return this;
        }

        public ErrorDto<T> build() {
            ErrorDto<T> errorDto = new ErrorDto<>();
            errorDto.setErrorCode(errorCode);
            errorDto.setErrors(errors);
            errorDto.setTimestamp(timestamp);

            return errorDto;
        }
    }
}