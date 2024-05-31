package com.learn.sayur.product;

import com.learn.sayur.exception.ApplicationException;
import com.learn.sayur.exception.DataNotFoundException;
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
    public ResponseEntity<Response<Product>> createProduct(@RequestBody ProductWithMetadataDTO productDTO) {
        try {
            Product product = modelMapper.map(productDTO, Product.class);
            Metadata metadata = modelMapper.map(productDTO.getMetadata(), Metadata.class);
            metadata.setProduct(product);
            product.setMetadata(metadata);
            Product createdProduct = productService.createProduct(product);
            return Response.successfulResponse("Product created successfully", createdProduct);
        } catch (Exception ex) {
            throw new ApplicationException("Failed to create product: " + ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Response<List<ProductDTO>>> getAllProducts(@RequestParam(required = false) String search) {
        try {
            List<Product> products = search != null && !search.isEmpty() ?
                    productService.getProductsBySearch(search) :
                    productService.getAllProducts();

            List<ProductDTO> productDTOs = products.stream()
                    .map(product -> modelMapper.map(product, ProductDTO.class))
                    .collect(Collectors.toList());

            return Response.successfulResponse("Products retrieved successfully", productDTOs);
        } catch (Exception ex) {
            throw new ApplicationException("Failed to retrieve products: " + ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ProductWithMetadataDTO>> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product == null) {
                throw new DataNotFoundException("Product not found with ID: " + id);
            }

            ProductWithMetadataDTO productDTO = modelMapper.map(product, ProductWithMetadataDTO.class);
            if (product.getMetadata() != null) {
                productDTO.setMetadata(modelMapper.map(product.getMetadata(), MetadataDTO.class));
            }

            return Response.successfulResponse("Product retrieved successfully", productDTO);
        } catch (DataNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException("Failed to retrieve product: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Product>> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            if (updatedProduct == null) {
                throw new DataNotFoundException("Product not found with ID: " + id);
            }
            return Response.successfulResponse("Product updated successfully", updatedProduct);
        } catch (DataNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException("Failed to update product: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return Response.successfulResponse("Product deleted successfully");
        } catch (DataNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException("Failed to delete product: " + ex.getMessage());
        }
    }
}
