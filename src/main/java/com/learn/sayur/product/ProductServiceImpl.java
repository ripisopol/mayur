package com.learn.sayur.product;

import com.learn.sayur.product.DTO.MetadataDTO;
import com.learn.sayur.product.entity.Metadata;
import com.learn.sayur.product.entity.Product;
import com.learn.sayur.product.repository.MetadataRepository;
import com.learn.sayur.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MetadataRepository metadataRepository;

    @Override
    public Product createProduct(Product product) {
        // Assign a unique ID by incrementing the maximum existing ID by 1
        Long maxId = productRepository.findMaxId();
        product.setId(maxId != null ? maxId + 1 : 1L);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsBySearch(String search) {
        return productRepository.findByNameContainingIgnoreCase(search);
    }

    // Service method for fetching a single product by ID with metadata
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public MetadataDTO getMetadataForProduct(Long id) {
        Product product = getProductById(id);
        Metadata metadata = metadataRepository.findByProductId(id);
        return mapMetadataToDTO(metadata);
    }
    @Override
    public Product saveProduct(Product product) {
        // Save metadata first if it's not already persisted
        Metadata metadata = product.getMetadata();
        if (metadata != null && metadata.getId() == null) {
            metadata = metadataRepository.save(metadata);
        }

        // Set the saved metadata back to the product
        product.setMetadata(metadata);

        // Save the product with the updated metadata
        return productRepository.save(product);
    }


    // Helper method to map Metadata entity to MetadataDTO
    private MetadataDTO mapMetadataToDTO(Metadata metadata) {
        MetadataDTO metadataDTO = new MetadataDTO();
        metadataDTO.setUnit(metadata.getUnit());
        metadataDTO.setWeight(metadata.getWeight());
        metadataDTO.setCalorie(metadata.getCalorie());
        metadataDTO.setProteins(metadata.getProteins());
        metadataDTO.setFats(metadata.getFats());
        metadataDTO.setIncrement(metadata.getIncrement());
        metadataDTO.setCarbs(metadata.getCarbs());
        return metadataDTO;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = getProductById(id);

        if (existingProduct == null) {
            return null; // Or throw an exception indicating product not found
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
            // If metadata is present, map DTO to entity
            Metadata newMetadataDTO = product.getMetadata();
            Metadata existingMetadata = existingProduct.getMetadata();

            if (existingMetadata == null) {
                existingMetadata = new Metadata();
                existingMetadata.setProduct(existingProduct);
            }

            // Update metadata fields using manual mapping
            existingMetadata.setUnit(newMetadataDTO.getUnit());
            existingMetadata.setWeight(newMetadataDTO.getWeight());
            existingMetadata.setCalorie(newMetadataDTO.getCalorie());
            existingMetadata.setProteins(newMetadataDTO.getProteins());
            existingMetadata.setFats(newMetadataDTO.getFats());
            existingMetadata.setIncrement(newMetadataDTO.getIncrement());
            existingMetadata.setCarbs(newMetadataDTO.getCarbs());

            existingProduct.setMetadata(existingMetadata);
        }

        return productRepository.save(existingProduct);
    }






    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        if (product != null) {
            // Delete associated metadata if it exists
            Metadata metadata = product.getMetadata();
            if (metadata != null) {
                metadataRepository.delete(metadata);
            }
            // Now delete the product
            productRepository.delete(product);
        }
    }


}