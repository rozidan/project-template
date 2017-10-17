package com.company.app.client.web.dtos.errors;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Idan Rozenfeld
 */
@ApiModel("ValidationError")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"fieldName", "errorCode"})
public class ValidationErrorDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(readOnly = true)
    private String fieldName;

    @ApiModelProperty(readOnly = true)
    private String errorCode;

    @ApiModelProperty(readOnly = true)
    private Object rejectedValue;

    @ApiModelProperty(readOnly = true)
    private Object[] params;
}