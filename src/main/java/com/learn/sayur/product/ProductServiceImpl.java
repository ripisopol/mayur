package com.learn.sayur.product;

import com.learn.sayur.product.DTO.MetadataDTO;
import com.learn.sayur.product.model.Metadata;
import com.learn.sayur.product.model.Product;
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
    public List<Product> getAllProducts() {
        return productRepository.findAll();
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

    // Other methods...
}


//    @Override
//    public Product createProduct(Product product) {
//        return productRepository.save(product);
//    }
//
//    @Override
//    public Product updateProduct(Long id, Product product) {
//        Product existingProduct = getProductById(id);
//        existingProduct.setName(product.getName());
//        existingProduct.setPrice(product.getPrice());
//        existingProduct.setCategory(product.getCategory());
//        existingProduct.setImageUrl(product.getImageUrl());
//        existingProduct.setWeight(product.getWeight());
//        existingProduct.setMetadata(product.getMetadata());
//        return productRepository.save(existingProduct);
//    }
//
//    @Override
//    public void deleteProduct(Long id) {
//        Product existingProduct = getProductById(id);
//        productRepository.delete(existingProduct);
//    }

