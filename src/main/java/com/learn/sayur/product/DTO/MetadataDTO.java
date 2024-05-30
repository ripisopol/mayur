package com.learn.sayur.product.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class MetadataDTO {
    @NotBlank(message = "Unit is required")
    private String unit;

    @Min(value = 0, message = "Weight must be non-negative")
    private Double weight; // Change the type to double

    @Min(value = 0, message = "Calorie count must be non-negative")
    private Integer calorie;

    @Min(value = 0, message = "Proteins must be non-negative")
    private Double proteins;

    @Min(value = 0, message = "Fats must be non-negative")
    private Double fats;

    @Min(value = 0, message = "Increment must be non-negative")
    private Integer increment;

    @Min(value = 0, message = "Carbs must be non-negative")
    private Double carbs;
}