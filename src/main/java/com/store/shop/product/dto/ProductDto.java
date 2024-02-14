package com.store.shop.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String id;
    private String title;
    private String description;
    private Integer price;
    private Integer discountPercentage;
    private Double rating;
    private Integer stock;
    private String brand;
    private String categoryId;
    private List<String> subcategories;
    private String thumbnail;
    private List<String> images;
}
