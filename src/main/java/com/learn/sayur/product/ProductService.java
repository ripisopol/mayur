package com.learn.sayur.product;

import com.learn.sayur.product.DTO.MetadataDTO;
import com.learn.sayur.product.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    List<Product> getProductsBySearch(String search);

    Product getProductById(Long id);
    MetadataDTO getMetadataForProduct(Long id);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}
