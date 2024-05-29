package com.learn.sayur.product;

import com.learn.sayur.product.DTO.MetadataDTO;
import com.learn.sayur.product.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    MetadataDTO getMetadataForProduct(Long id);
//    Product createProduct(Product product);
//    Product updateProduct(Long id, Product product);
//    void deleteProduct(Long id);
}
