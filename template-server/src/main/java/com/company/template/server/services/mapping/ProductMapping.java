package com.company.template.server.services.mapping;

import com.company.template.client.web.dtos.ProductDto;
import com.company.template.server.domain.model.Product;
import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

/**
 * @author Idan Rozenfeld
 */
public class ProductMapping {

    @Component
    public static class ProductToDtoMapping extends TypeMapConfigurer<Product, ProductDto> {

        @Override
        public void configure(TypeMap<Product, ProductDto> typeMap) {
            typeMap.addMapping(Product::getDesc, ProductDto::setDescription);
        }
    }

    @Component
    public static class ProductFromDtoMapping extends TypeMapConfigurer<ProductDto, Product> {

        @Override
        public void configure(TypeMap<ProductDto, Product> typeMap) {
            typeMap.addMappings(map -> map.skip(Product::setId));
            typeMap.addMapping(ProductDto::getDescription, Product::setDesc);
        }
    }

}