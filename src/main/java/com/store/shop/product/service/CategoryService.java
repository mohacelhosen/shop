package com.store.shop.product.service;

import com.store.shop.exception.DuplicateKeyException;
import com.store.shop.exception.NotFoundException;
import com.store.shop.product.model.Category;
import com.store.shop.product.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category saveCategory(Category category) {
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory.isPresent()) {
            throw new DuplicateKeyException("Category with the same name already exists");
        }
        // Save the category
        return categoryRepository.save(category);
    }

    public Category findByCategoryName(String categoryName){
        Optional<Category> optionalCategory = categoryRepository.findByName(categoryName);
        // Use orElseThrow to throw a NotFoundException if the category is not found
        return optionalCategory.orElseThrow(() -> new NotFoundException("Category not found"));
    }

    public Category findByCategoryId(String categoryId){
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        // Use orElseThrow to throw a NotFoundException if the category is not found
        return optionalCategory.orElseThrow(() -> new NotFoundException("Category not found"));
    }
    public Category updateCategoryInfo(Category category) {
        if (category.getId() == null) {
            throw new IllegalArgumentException("Category ID can't be null");
        }

        Optional<Category> optionalCategory = categoryRepository.findById(category.getId());
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("Category not found");
        }

        Category dbCategory = optionalCategory.get();

        // Update only if the new value is not null
        if (category.getParentCategoryId() != null) {
            dbCategory.setParentCategoryId(category.getParentCategoryId());
        }
        if (category.getSubcategories() != null) {
            dbCategory.setSubcategories(category.getSubcategories());
        }
        if (category.getImageUrl() != null) {
            dbCategory.setImageUrl(category.getImageUrl());
        }

        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory.isPresent()) {
            throw new DuplicateKeyException("Category with the same name already exists");
        }
        // Only update the name if it is not null
        if (category.getName() != null) {
            dbCategory.setName(category.getName());
        }

        return categoryRepository.save(dbCategory);
    }



    public List<Category> findAllCategory(){
        return categoryRepository.findAll();
    }
}
