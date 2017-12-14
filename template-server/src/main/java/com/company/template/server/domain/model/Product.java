package com.company.template.server.domain.model;

import com.company.template.server.domain.model.Constraints.ProductNameUnique;
import com.company.template.server.domain.model.types.ProductCategory;
import com.company.template.server.domain.model.Constraints.ProductNameUnique;
import com.company.template.server.domain.model.types.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Idan Rozenfeld
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@Table(uniqueConstraints =
        {@UniqueConstraint(name = ProductNameUnique.CONSTRAINT_NAME, columnNames = ProductNameUnique.FIELD_NAME)})
@SequenceGenerator(name = "productSeq", sequenceName = "product_seq")
public class Product extends AuditableEntity {

    @Id
    @GeneratedValue(generator = "productSeq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    @Column(nullable = false, name = ProductNameUnique.FIELD_NAME)
    private String name;

    @Lob
    private String desc;

    @Min(10)
    @NotNull
    @Column(nullable = false)
    private Float unitPrice;

    @Version
    private long version;

    private ProductCategory category;

    @ElementCollection
    Set<ProductCatalog> catalogs;
}