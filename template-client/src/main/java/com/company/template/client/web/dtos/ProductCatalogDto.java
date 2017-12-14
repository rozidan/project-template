package com.company.template.client.web.dtos;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;

/**
 * @author Idan Rozenfeld
 */
@ApiModel("ProductCatalog")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class ProductCatalogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private int id;
}
