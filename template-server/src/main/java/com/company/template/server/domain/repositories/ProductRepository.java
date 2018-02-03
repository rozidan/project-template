package com.company.template.server.domain.repositories;

import com.company.template.server.domain.model.Product;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

/**
 * @author Idan Rozenfeld
 */
@RepositoryDefinition(domainClass = Product.class, idClass = Long.class)
public interface ProductRepository {
    List<Product> findAll();

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findOne(long id);

    Product save(Product product);

    void delete(Product product);

    @Query("SELECT AVG(p.unitPrice) from Product p")
    float getAverage();

    @Query("SELECT count(p) > 0 FROM Product p WHERE p.name = :name AND (:id is null or p.id = :id)")
    boolean existsByNameAndIdNot(@Param("name") String name, @Param("id") Long id);
}