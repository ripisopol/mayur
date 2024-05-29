package com.learn.sayur.product.DTO;

import lombok.Data;


@Data

public class MetadataDTO {
    private String unit;
    private Double weight;
    private Integer calorie;
    private Double proteins;
    private Double fats;
    private Integer increment;
    private Double carbs;

}