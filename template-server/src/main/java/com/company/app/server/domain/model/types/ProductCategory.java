package com.company.app.server.domain.model.types;

import com.company.app.client.EnumUtils;
import com.company.app.client.IdentifierType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * @author Idan Rozenfeld
 */
public enum ProductCategory implements IdentifierType<String> {
    GAME("G"), CLOTHING("Clth");

    private final String id;

    ProductCategory(String id) {
        this.id = id;
    }

    public static ProductCategory byValue(String value) {
        if (Objects.nonNull(value)) {
            return EnumUtils.getByValue(ProductCategory.class, value);
        }

        return null;
    }

    @Override
    public String getValue() {
        return id;
    }

    @Converter(autoApply = true)
    public static class ProductCategoryConverter implements AttributeConverter<ProductCategory, String> {

        @Override
        public String convertToDatabaseColumn(ProductCategory attribute) {
            if (Objects.nonNull(attribute)) {
                return attribute.getValue();
            }
            return null;
        }

        @Override
        public ProductCategory convertToEntityAttribute(String dbData) {
            if (Objects.nonNull(dbData)) {
                return ProductCategory.byValue(dbData);
            }
            return null;
        }

    }
}
