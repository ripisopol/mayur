package com.learn.sayur.product.DTO;

import com.learn.sayur.product.model.Metadata;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private String category;
    private String imageUrl;
    private Double weight;
}
