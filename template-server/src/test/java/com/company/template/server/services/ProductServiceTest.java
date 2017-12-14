package com.company.template.server.services;

import com.company.template.server.domain.model.Product;
import com.company.template.server.domain.model.types.ProductCategory;
import com.company.template.server.domain.repositories.ProductRepository;
import com.company.template.server.services.impl.ProductServiceImpl;
import com.company.template.server.services.mapping.MappingBasePackage;
import com.github.rozidan.springboot.modelmapper.WithModelMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WithModelMapper(basePackageClasses = MappingBasePackage.class)
@ContextConfiguration(classes = ProductServiceImpl.class)
public class ProductServiceTest {

    @Autowired
    private ProductService service;

    @MockBean
    private ProductRepository repository;

    @Test
    public void calculateAverage() {
        Product product = Product.builder()
                .name("John")
                .category(ProductCategory.GAME)
                .unitPrice(100F)
                .desc("desc")
                .build();
        Product product2 = Product.builder()
                .name("Mario")
                .category(ProductCategory.GAME)
                .unitPrice(51F)
                .desc("desc")
                .build();
        when(repository.findAll()).thenReturn(Arrays.asList(product, product2));
        float result = service.getProductPriceAvg();
        assertThat(result, is(equalTo(75.5F)));
    }

}
