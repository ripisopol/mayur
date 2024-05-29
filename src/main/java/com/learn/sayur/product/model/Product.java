package com.learn.sayur.product.model;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String category;
    private String imageUrl;
    private Double weight;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private Metadata metadata;
}
