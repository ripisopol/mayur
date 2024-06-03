package com.learn.sayur.favorite_product;

import com.learn.sayur.exception.DataNotFoundException;
import com.learn.sayur.product.entity.Product;
import com.learn.sayur.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final ProductRepository productRepository;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, ProductRepository productRepository) {
        this.favoriteRepository = favoriteRepository;
        this.productRepository = productRepository;
    }

    public boolean toggleFavoriteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found with id: " + productId));

        Favorite favoriteProduct = favoriteRepository.findByProductId(productId)
                .orElse(null);

        if (favoriteProduct == null) {
            favoriteRepository.save(new Favorite(productId));
            return true;
        } else {
            favoriteRepository.delete(favoriteProduct);
            return false;
        }
    }

    public List<Product> getFavoriteProducts() {
        List<Favorite> favoriteProducts = favoriteRepository.findAll();
        return favoriteProducts.stream()
                .map(favoriteProduct -> productRepository.findById(favoriteProduct.getProductId())
                        .orElseThrow(() -> new DataNotFoundException("Product not found with id: " + favoriteProduct.getProductId())))
                .collect(Collectors.toList());
    }
}
