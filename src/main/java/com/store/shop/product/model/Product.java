package com.store.shop.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "PRODUCT")
public class Product {
    @Id
    private Integer id;
    private String title;
    private String description;
    private Integer price;
    private Integer discountPercentage;
    private Double rating;
    private Integer stock;
    private String brand;
    private Category category;
    private String thumbnail;
    private List<String> images;
}
