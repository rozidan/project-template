package com.company.app.client.web.dtos.audit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Idan Rozenfeld
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@ApiModel("Audit")
public class AuditableDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(readOnly = true)
    private String createdBy;

    @ApiModelProperty(readOnly = true)
    private Calendar createdDate;

    @ApiModelProperty(readOnly = true)
    private String lastModifiedBy;

    @ApiModelProperty(readOnly = true)
    private Calendar lastModifiedDate;
}