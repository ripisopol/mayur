package com.learn.sayur.product.repository;

import com.learn.sayur.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    @Query("SELECT p FROM products p LEFT JOIN FETCH p.metadata m WHERE p.id = :id")
//    Optional<Product> findByIdWithMetadata(@Param("id") Long id);
}

