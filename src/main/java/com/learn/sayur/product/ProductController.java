package com.learn.sayur.product;

import com.learn.sayur.product.DTO.MetadataDTO;
import com.learn.sayur.product.DTO.ProductDTO;
import com.learn.sayur.product.DTO.ProductWithMetadataDTO;
import com.learn.sayur.product.entity.Metadata;
import com.learn.sayur.product.entity.Product;
import com.learn.sayur.response.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductWithMetadataDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        Metadata metadata = modelMapper.map(productDTO.getMetadata(), Metadata.class);
        metadata.setProduct(product);
        product.setMetadata(metadata);
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping
    public List<ProductDTO> getAllProducts(@RequestParam(required = false) String search) {
        List<Product> products = search != null && !search.isEmpty() ?
                productService.getProductsBySearch(search) :
                productService.getAllProducts();

        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductWithMetadataDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        ProductWithMetadataDTO productDTO = modelMapper.map(product, ProductWithMetadataDTO.class);
        if (product.getMetadata() != null) {
            productDTO.setMetadata(modelMapper.map(product.getMetadata(), MetadataDTO.class));
        }

        return ResponseEntity.ok(productDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return updatedProduct != null ? ResponseEntity.ok(updatedProduct) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
