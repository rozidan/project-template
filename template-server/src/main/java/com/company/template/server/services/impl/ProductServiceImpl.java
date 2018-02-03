package com.company.template.server.services.impl;

import com.company.template.client.web.dtos.ProductDto;
import com.company.template.server.domain.model.Product;
import com.company.template.server.domain.repositories.ProductRepository;
import com.company.template.server.services.ProductService;
import com.company.template.server.web.handlers.exceptions.UniqueFieldException;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Idan Rozenfeld
 */
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ProductServiceImpl implements ProductService {

    private final ModelMapper mapper;

    private final ProductRepository repository;

    @Override
    @Transactional
    public long catalogue(ProductDto productDto) {
        checkUniqueName(null, productDto.getName());
        Product product = mapper.map(productDto, Product.class);
        Product newProd = repository.save(product);
        return newProd.getId();
    }


    @Override
    @Transactional
    public void replace(ProductDto productDto, long id) {
        checkUniqueName(id, productDto.getName());
        Product product = repository.findOne(id).orElseThrow(this::createEmptyResultException);
        mapper.map(productDto, product);
    }

    @Override
    public Page<ProductDto> fetch(Pageable pageable) {
        Page<Product> products = repository.findAll(pageable);
        return products.map(product -> mapper.map(product, ProductDto.class));
    }

    @Override
    @Transactional
    public void remove(long id) {
        Product product = repository.findOne(id).orElseThrow(this::createEmptyResultException);
        repository.delete(product);
    }

    @Override
    public ProductDto get(long id) {
        Product product = repository.findOne(id).orElseThrow(this::createEmptyResultException);
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public float getProductPriceAvg() {
        return repository.getAverage();
    }

    private EmptyResultDataAccessException createEmptyResultException() {
        return new EmptyResultDataAccessException(1);
    }

    private void checkUniqueName(Long id, String name) {
        if (repository.existsByNameAndIdNot(name, id)) {
            throw new UniqueFieldException("Product name must be unique", "name", name);
        }
    }
}