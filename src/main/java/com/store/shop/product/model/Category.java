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
@Document(value = "CATEGORY")
public class Category {
    @Id
    private String id;
    private String name;
    private String description;
    private String imageUrl; // URL to an image representing the category
    private String parentCategoryId; // If the category has a parent category
    private List<String> subcategories; // List of subcategories under this category
    private boolean active;
}
