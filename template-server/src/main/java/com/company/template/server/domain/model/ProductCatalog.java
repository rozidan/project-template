package com.company.template.server.domain.model;

import lombok.*;

import javax.persistence.Embeddable;

/**
 * @author Idan Rozenfeld
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
@Embeddable
public class ProductCatalog {
    private String name;
    private int id;
}
