package com.company.template.server.services;

import com.company.template.client.web.dtos.ProductDto;
import com.company.template.client.web.dtos.TagDto;
import com.company.template.client.web.dtos.types.ProductCategoryDto;
import com.company.template.server.domain.repositories.ProductRepository;
import com.company.template.server.services.impl.ProductServiceImpl;
import com.company.template.server.services.mapping.MappingBasePackage;
import com.company.template.server.web.handlers.exceptions.UniqueFieldException;
import com.github.rozidan.springboot.modelmapper.WithModelMapper;

import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


@RunWith(SpringRunner.class)
@WithModelMapper(basePackageClasses = MappingBasePackage.class)
@ContextConfiguration(classes = ProductServiceImpl.class)
public class ProductServiceTest {

    @Autowired
    private ProductService service;

    @MockBean
    private ProductRepository repository;

    @Test
    public void removeNotExistsShouldFailed() {
        Mockito.when(repository.findOne(1)).thenReturn(Optional.empty());

        try {
            service.remove(1);
        } catch (Exception ex) {
            assertTrue(ex instanceof EmptyResultDataAccessException);
        }

        verify(repository).findOne(1);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void catalogUniqueNameShouldHandleError() {
        ProductDto productDto = ProductDto.builder()
                .name("John")
                .category(ProductCategoryDto.CLOTHING)
                .unitPrice(100.0F)
                .tag(TagDto.of("clo", 2))
                .build();

        Mockito.when(repository.existsByNameAndIdNot("John", null)).thenReturn(true);

        try {
            service.catalogue(productDto);
        } catch (Exception ex) {
            assertTrue(ex instanceof UniqueFieldException);
            UniqueFieldException ufe = (UniqueFieldException) ex;
            assertThat(ufe.getField(), is(equalTo("name")));
            assertThat(ufe.getRejectedValue(), is(equalTo("John")));
        }

        verify(repository).existsByNameAndIdNot("John", null);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void replaceUniqueNameShouldHandleError() {
        ProductDto productDto = ProductDto.builder()
                .name("John")
                .category(ProductCategoryDto.CLOTHING)
                .unitPrice(100.0F)
                .tag(TagDto.of("clo", 2))
                .build();

        Mockito.when(repository.existsByNameAndIdNot("John", 1L)).thenReturn(true);

        try {
            service.replace(productDto, 1L);
        } catch (Exception ex) {
            assertTrue(ex instanceof UniqueFieldException);
            UniqueFieldException ufe = (UniqueFieldException) ex;
            assertThat(ufe.getField(), is(equalTo("name")));
            assertThat(ufe.getRejectedValue(), is(equalTo("John")));
        }

        verify(repository).existsByNameAndIdNot("John", 1L);
        verifyNoMoreInteractions(repository);
    }
}
