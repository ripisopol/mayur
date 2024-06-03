package com.learn.sayur.favorite_product;

import com.learn.sayur.product.entity.Product;
import java.util.List;

public interface FavoriteService {
    boolean toggleFavoriteProduct(Long productId);
    List<Product> getFavoriteProducts();
}