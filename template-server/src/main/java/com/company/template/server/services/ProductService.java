package com.company.template.server.services;

import com.company.template.client.web.dtos.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Idan Rozenfeld
 */
public interface ProductService {

    ProductDto catalogue(ProductDto empDto);

    Page<ProductDto> fetch(Pageable pageable);

    void remove(long id);

    ProductDto get(long id);

    float getProductPriceAvg();
}