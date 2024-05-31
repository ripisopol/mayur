package com.learn.sayur.product;

import com.learn.sayur.exception.ApplicationException;
import com.learn.sayur.exception.DataNotFoundException;
import com.learn.sayur.product.DTO.MetadataDTO;
import com.learn.sayur.product.entity.Metadata;
import com.learn.sayur.product.entity.Product;
import com.learn.sayur.product.repository.MetadataRepository;
import com.learn.sayur.product.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MetadataRepository metadataRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, MetadataRepository metadataRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.metadataRepository = metadataRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Product createProduct(Product product) {
        // Save product first
        Product savedProduct = productRepository.save(product);

        // Save metadata and link it with the saved product
        Metadata metadata = product.getMetadata();
        if (metadata != null) {
            metadata.setProduct(savedProduct);
            metadataRepository.save(metadata);
        }

        // Return the saved product
        return savedProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsBySearch(String search) {
        return productRepository.findByNameContainingIgnoreCase(search);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public MetadataDTO getMetadataForProduct(Long id) {
        Metadata metadata = metadataRepository.findByProductId(id);
        return mapMetadataToDTO(metadata);
    }

    // Helper method to map Metadata entity to MetadataDTO
    private MetadataDTO mapMetadataToDTO(Metadata metadata) {
        return modelMapper.map(metadata, MetadataDTO.class);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = getProductById(id);

        if (existingProduct == null) {
            throw new DataNotFoundException("Product not found with ID: " + id);
        }

        // Update only the fields that are not null in the request body
        if (product.getName() != null) {
            existingProduct.setName(product.getName());
        }
        if (product.getPrice() != null) {
            existingProduct.setPrice(product.getPrice());
        }
        if (product.getCategory() != null) {
            existingProduct.setCategory(product.getCategory());
        }
        if (product.getImageUrl() != null) {
            existingProduct.setImageUrl(product.getImageUrl());
        }
        if (product.getWeight() != null) {
            existingProduct.setWeight(product.getWeight());
        }
        if (product.getMetadata() != null) {
            Metadata newMetadata = product.getMetadata();
            Metadata existingMetadata = existingProduct.getMetadata();

            if (existingMetadata == null) {
                existingMetadata = new Metadata();
                existingMetadata.setProduct(existingProduct);
            }

            // Update metadata fields using manual mapping
            modelMapper.map(newMetadata, existingMetadata);

            existingProduct.setMetadata(existingMetadata);
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        if (product != null) {
            productRepository.delete(product);
        } else {
            throw new DataNotFoundException("Product not found with ID: " + id);
        }
    }

    @Override
    public Product saveProduct(Product product) {
        return createProduct(product);
    }
}
