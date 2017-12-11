package com.company.template.server.domain.repositories;

import com.company.template.server.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;
import java.util.Optional;

/**
 * @author Idan Rozenfeld
 */
@RepositoryDefinition(domainClass = Product.class, idClass = Long.class)
public interface ProductRepository {
    List<Product> findAll();

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findOne(long id);

    Product save(Product product);

    void delete(long id);

}