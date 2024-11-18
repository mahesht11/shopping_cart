package com.ecom.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String title;
    private String description;
    private Integer stock;
    private Double price;
    private String imageName;
    private Double discountPrice;
    private Integer discountPersentage;
    private CategoryDto category;
}
