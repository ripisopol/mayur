package com.learn.sayur.product;

import com.learn.sayur.exception.ApplicationException;
import com.learn.sayur.exception.DataNotFoundException;
import com.learn.sayur.product.DTO.MetadataDTO;
import com.learn.sayur.product.entity.Metadata;
import com.learn.sayur.product.entity.Product;
import com.learn.sayur.product.repository.MetadataRepository;
import com.learn.sayur.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
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
    @Transactional
    public Product createProduct(Product product) {
        // Save product and metadata together
        product = productRepository.save(product);

        // Check if metadata exists and link it with the saved product
        Metadata metadata = product.getMetadata();
        if (metadata != null) {
            metadata.setProduct(product);
            metadataRepository.save(metadata);
        }

        return product;
    }

    @Override
    @Transactional
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public List<Product> getProductsBySearch(String search) {
        return productRepository.findByNameContainingIgnoreCase(search);
    }

    @Override
    @Transactional
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public MetadataDTO getMetadataForProduct(Long id) {
        Metadata metadata = metadataRepository.findByProductId(id);
        return modelMapper.map(metadata, MetadataDTO.class);
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = getProductById(id);

        if (existingProduct == null) {
            throw new DataNotFoundException("Product not found with ID: " + id);
        }

        // Update product fields
        modelMapper.map(product, existingProduct);

        return productRepository.save(existingProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        if (product == null) {
            throw new DataNotFoundException("Product not found with ID: " + id);
        }
        productRepository.delete(product);
    }
}
