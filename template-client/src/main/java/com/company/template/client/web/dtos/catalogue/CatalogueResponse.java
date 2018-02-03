package com.company.template.client.web.dtos.catalogue;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Idan Rozenfeld
 */
@ApiModel("Catalogue Response")
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class CatalogueResponse {
    private long productId;
}
