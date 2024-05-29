package com.learn.sayur.product.DTO;

import lombok.Data;

@Data
public class ProductWithMetadataDTO {
    private Long id;
    private String name;
    private double price;
    private String category;
    private String imageUrl;
    private Double weight;
    private MetadataDTO metadata;
}