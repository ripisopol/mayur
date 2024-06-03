package com.learn.sayur.product.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Image Url is required")
    private String imageUrl;

    @Min(value = 0, message = "price must be non-negative")
    private Double price;

    @Min(value = 0, message = "Weight must non-negative")
    private Double weight;

    public ProductDTO(Long id) {
    }
}
