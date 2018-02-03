package com.company.template.client.web.dtos.catalogue;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Idan Rozenfeld
 */
@ApiModel("Avg Price Response")
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class AvgPriceResponse {
    private float avg;
}
