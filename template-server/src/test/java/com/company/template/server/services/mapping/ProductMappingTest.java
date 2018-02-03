package com.company.template.server.services.mapping;

import com.company.template.client.web.dtos.ProductDto;
import com.company.template.client.web.dtos.TagDto;
import com.company.template.client.web.dtos.types.ProductCategoryDto;
import com.company.template.server.domain.model.Product;
import com.company.template.server.domain.model.types.ProductCategory;
import com.github.rozidan.springboot.modelmapper.WithModelMapper;

import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WithModelMapper(basePackageClasses = MappingBasePackage.class)
public class ProductMappingTest {

    @Autowired
    private ModelMapper mapper;

    @Test
    public void productDtoToEntityMappedSuccess() {
        ProductDto dto = ProductDto.builder()
                .name("John")
                .description("Desc")
                .category(ProductCategoryDto.CLOTHING)
                .unitPrice(100f)
                .tag(TagDto.of("clo", 2))
                .tag(TagDto.of("fit", 1))
                .build();

        Product result = mapper.map(dto, Product.class);

        assertThat(result.getName(), is(equalTo("John")));
        assertThat(result.getUnitPrice(), is(equalTo(100F)));
        //Enum mapped successfully cause modelmapper doing it by .toString() method
        assertThat(result.getCategory(), is(equalTo(ProductCategory.CLOTHING)));
        assertThat(result.getDesc(), is(equalTo("Desc")));
        assertThat(result.getTags(), hasSize(2));
        assertThat(result.getTags(), containsInAnyOrder(Arrays.asList(
                allOf(hasProperty("caption", is(equalTo("clo"))),
                        hasProperty("level", is(equalTo(2)))),
                allOf(hasProperty("caption", is(equalTo("fit"))),
                        hasProperty("level", is(equalTo(1)))))
        ));
    }
}
