package com.learn.sayur.product.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learn.sayur.product.entity.Product;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@Entity
@Table(name = "metadata")
public class Metadata {
    @Id
    @Column(name = "id")
    private Long id;  // This is the foreign key referencing the products table

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId  // Maps the id attribute to the primary key of the associated Product entity
    @JoinColumn(name = "id")
    @JsonIgnore
    private Product product;

    private String unit;
    private Double weight;
    private Integer calorie;
    private Double proteins;
    private Double fats;
    private Integer increment;
    private Double carbs;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(nullable = true)
    @UpdateTimestamp
    private Instant updatedAt;

    @Column(nullable = true)
    private Instant deletedAt;
}
