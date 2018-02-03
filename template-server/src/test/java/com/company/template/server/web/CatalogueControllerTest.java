package com.company.template.server.web;

import com.company.template.client.web.dtos.ProductDto;
import com.company.template.client.web.dtos.TagDto;
import com.company.template.client.web.dtos.errors.ErrorCodes;
import com.company.template.client.web.dtos.types.ProductCategoryDto;
import com.company.template.server.config.JsonConfiguration;
import com.company.template.server.services.ProductService;
import com.company.template.server.web.controllers.CatalogueController;
import com.company.template.server.web.handlers.exceptions.UniqueFieldException;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JsonConfiguration.class)
@WebMvcTest(secure = false, controllers = CatalogueController.class)
public class CatalogueControllerTest {

    @Autowired
    private ObjectWriter writer;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService service;

    @Test
    public void catalogueSuccess() throws Exception {
        ProductDto request = ProductDto.builder()
                .name("John")
                .category(ProductCategoryDto.CLOTHING)
                .unitPrice(100.0F)
                .tag(TagDto.of("clo", 2))
                .build();

        when(service.catalogue(any(ProductDto.class))).thenReturn(1L);

        mvc.perform(post("/catalogue")
                .content(writer.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId", is(equalTo(1))));

        verify(service).catalogue(any(ProductDto.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void removeNotExistsShouldHandleError() throws Exception {
        doThrow(new EmptyResultDataAccessException(1)).when(service).remove(1);

        mvc.perform(delete("/catalogue/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(service).remove(1);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void catalogUniqueNameShouldHandleError() throws Exception {
        ProductDto request = ProductDto.builder()
                .name("John")
                .category(ProductCategoryDto.CLOTHING)
                .unitPrice(100.0F)
                .tag(TagDto.of("clo", 2))
                .build();

        when(service.catalogue(request)).thenThrow(new UniqueFieldException("bla", "name", "1"));

        mvc.perform(post("/catalogue")
                .content(writer.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode", is(equalTo(ErrorCodes.DATA_VALIDATION.toString()))))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].fieldName", is(equalTo("name"))))
                .andExpect(jsonPath("$.errors[0].errorCode", is(equalTo("UNIQUE"))))
                .andExpect(jsonPath("$.errors[0].rejectedValue", is(equalTo("1"))));

        verify(service).catalogue(any(ProductDto.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void avgPriceSuccess() throws Exception {
        when(service.getProductPriceAvg()).thenReturn(10.0F);

        mvc.perform(get("/catalogue/avgPrice")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.avg", is(equalTo(10.0))));

        verify(service).getProductPriceAvg();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void catalogueEmptyNameShouldFailed() throws Exception {
        ProductDto request = ProductDto.builder()
                .name("")
                .category(ProductCategoryDto.CLOTHING)
                .unitPrice(100.0F)
                .tag(TagDto.of("clo", 2))
                .build();

        mvc.perform(post("/catalogue")
                .content(writer.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode", is(equalTo(ErrorCodes.DATA_VALIDATION.toString()))))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].fieldName", is(equalTo("name"))))
                .andExpect(jsonPath("$.errors[0].errorCode", is(equalTo("NotEmpty"))))
                .andExpect(jsonPath("$.errors[0].rejectedValue", is(equalTo(""))))
                .andExpect(jsonPath("$.errors[0].params").isArray())
                .andExpect(jsonPath("$.errors[0].params").isEmpty());

        verifyNoMoreInteractions(service);
    }

    @Test
    public void catalogueUnitPriceLowerThen10ShouldFailed() throws Exception {
        ProductDto request = ProductDto.builder()
                .name("John")
                .category(ProductCategoryDto.CLOTHING)
                .unitPrice(8.5F)
                .tag(TagDto.of("clo", 2))
                .build();

        mvc.perform(post("/catalogue")
                .content(writer.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode", is(equalTo(ErrorCodes.DATA_VALIDATION.toString()))))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].fieldName", is(equalTo("unitPrice"))))
                .andExpect(jsonPath("$.errors[0].errorCode", is(equalTo("Min"))))
                .andExpect(jsonPath("$.errors[0].rejectedValue", is(equalTo(8.5))))
                .andExpect(jsonPath("$.errors[0].params").isArray())
                .andExpect(jsonPath("$.errors[0].params[0]", is(equalTo("10"))));

        verifyNoMoreInteractions(service);
    }


}
