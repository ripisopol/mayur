package com.learn.sayur.product;

import com.learn.sayur.product.DTO.MetadataDTO;
import com.learn.sayur.product.DTO.ProductDTO;
import com.learn.sayur.product.DTO.ProductWithMetadataDTO;
import com.learn.sayur.product.model.Metadata;
import com.learn.sayur.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOs = new ArrayList<>();

        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setCategory(product.getCategory());
            productDTO.setImageUrl(product.getImageUrl());
            productDTO.setWeight(product.getWeight());

            productDTOs.add(productDTO);
        }

        return productDTOs;
    }

    // Endpoint to get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductWithMetadataDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        ProductWithMetadataDTO productDTO = new ProductWithMetadataDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategory(product.getCategory());
        productDTO.setImageUrl(product.getImageUrl());
        productDTO.setWeight(product.getWeight());

        // Map metadata if it exists
        if (product.getMetadata() != null) {
            MetadataDTO metadataDTO = new MetadataDTO();
            metadataDTO.setUnit(product.getMetadata().getUnit());
            metadataDTO.setWeight(product.getMetadata().getWeight());
            metadataDTO.setCalorie(product.getMetadata().getCalorie());
            metadataDTO.setProteins(product.getMetadata().getProteins());
            metadataDTO.setFats(product.getMetadata().getFats());
            metadataDTO.setIncrement(product.getMetadata().getIncrement());
            metadataDTO.setCarbs(product.getMetadata().getCarbs());

            productDTO.setMetadata(metadataDTO);
        }

        return ResponseEntity.ok(productDTO);
    }

}


//    @PostMapping
//    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
//        Product createdProduct = productService.createProduct(product);
//        return ResponseEntity.ok(createdProduct);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
//        Product updatedProduct = productService.updateProduct(id, product);
//        return ResponseEntity.ok(updatedProduct);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
//        productService.deleteProduct(id);
//        return ResponseEntity.noContent().build();
//    }

