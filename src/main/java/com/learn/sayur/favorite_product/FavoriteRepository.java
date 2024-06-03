package com.learn.sayur.favorite_product;

import com.learn.sayur.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByProductId(Long productId);

}
