package com.company.template.client.web.dtos.errors;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.springframework.data.auditing.CurrentDateTimeProvider;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author Idan Rozenfeld
 *
 * It is recommended to replace the messages with those
 * that do not reveal details about the code.
 */
@ApiModel("Error")
@Getter
@Setter
@Builder
public class ErrorDto implements Serializable {
    private static final long serialVersionUID = -4708936233513887899L;

    private String errorCode;

    private String message;

    @Builder.Default
    private Date timestamp = CurrentDateTimeProvider.INSTANCE.getNow().getTime();

    @Singular
    private Set<Object> errors;
}