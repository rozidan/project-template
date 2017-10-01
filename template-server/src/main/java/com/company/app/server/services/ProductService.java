package com.company.app.server.services;

import com.company.app.client.web.dtos.ProductDto;
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