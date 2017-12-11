package com.company.template.client.web.dtos.errors;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Idan Rozenfeld
 */
@ApiModel("HttpMediaTypeError")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HttpMediaTypeErrorDto {
    private static final long serialVersionUID = 1L;

    private String mediaType;
}
