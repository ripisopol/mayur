package com.learn.sayur.product.entity;

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
    private Double price;
    private String category;
    private String imageUrl;
    private Double weight;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Metadata metadata;

}
