package com.company.template.server.services.mapping;

import com.company.template.client.web.dtos.ProductCatalogDto;
import com.company.template.client.web.dtos.ProductDto;
import com.company.template.client.web.dtos.types.ProductCategoryDto;
import com.company.template.server.domain.model.Product;
import com.company.template.server.domain.model.types.ProductCategory;
import com.github.rozidan.springboot.modelmapper.WithModelMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@WithModelMapper(basePackageClasses = MappingBasePackage.class)
public class ProductMappingTest {

    @Autowired
    private ModelMapper mapper;

    @Test
    public void productDtoToEntityMappedSuccess() {
        Set<ProductCatalogDto> catalog = new HashSet<>();
        catalog.add(ProductCatalogDto.builder().id(45).name("C1").build());
        catalog.add(ProductCatalogDto.builder().id(12).name("D2").build());

        ProductDto dto = ProductDto.builder()
                .id(1L)
                .name("John")
                .description("Desc")
                .category(ProductCategoryDto.CLOTHING)
                .unitPrice(100f)
                .catalogs(catalog)
                .build();

        Product result = mapper.map(dto, Product.class);

        assertThat(result.getId(), is(equalTo(1L)));
        assertThat(result.getName(), is(equalTo("John")));
        assertThat(result.getUnitPrice(), is(equalTo(100F)));
        //Enum mapped successfully cause modelmapper doing it by .toString() method
        assertThat(result.getCategory(), is(equalTo(ProductCategory.CLOTHING)));
        assertThat(result.getDesc(), is(equalTo("Desc")));
        assertThat(result.getCatalogs(), hasSize(2));
        assertThat(result.getCatalogs(), containsInAnyOrder(
                allOf(hasProperty("id", is(equalTo(45))),
                        hasProperty("name", is(equalTo("C1")))),
                allOf(hasProperty("id", is(equalTo(12))),
                        hasProperty("name", is(equalTo("D2"))))
        ));
    }
}
