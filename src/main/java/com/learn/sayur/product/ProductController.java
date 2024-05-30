package com.learn.sayur.product;

import com.learn.sayur.product.DTO.MetadataDTO;
import com.learn.sayur.product.DTO.ProductDTO;
import com.learn.sayur.product.DTO.ProductWithMetadataDTO;
import com.learn.sayur.product.entity.Metadata;
import com.learn.sayur.product.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductWithMetadataDTO productDTO) {
        // Map DTO to entity
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setCategory(productDTO.getCategory());
        product.setImageUrl(productDTO.getImageUrl());
        product.setWeight(productDTO.getWeight());

        // Create metadata entity
        MetadataDTO metadataDTO = productDTO.getMetadata();
        Metadata metadata = new Metadata();
        metadata.setUnit(metadataDTO.getUnit());
        metadata.setWeight(metadataDTO.getWeight());
        metadata.setCalorie(metadataDTO.getCalorie());
        metadata.setProteins(metadataDTO.getProteins());
        metadata.setFats(metadataDTO.getFats());
        metadata.setIncrement(metadataDTO.getIncrement());
        metadata.setCarbs(metadataDTO.getCarbs());

        // Set the association between product and metadata
        metadata.setProduct(product);
        product.setMetadata(metadata);

        // Save product and metadata
        Product createdProduct = productService.createProduct(product);

        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping
    public List<ProductDTO> getAllProducts(@RequestParam(required = false) String search) {
        List<Product> products;
        if (search != null && !search.isEmpty()) {
            products = productService.getProductsBySearch(search);
        } else {
            products = productService.getAllProducts();
        }

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

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);

        // Check if the product was successfully updated
        if (updatedProduct == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


}



