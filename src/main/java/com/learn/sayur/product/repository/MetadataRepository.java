package com.learn.sayur.product.repository;

import com.learn.sayur.product.entity.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Long> {
    Metadata findByProductId(Long productId);
}