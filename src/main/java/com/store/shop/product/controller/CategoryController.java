package com.store.shop.product.controller;

import com.store.shop.exception.DuplicateKeyException;
import com.store.shop.exception.NotFoundException;
import com.store.shop.product.model.Category;
import com.store.shop.product.service.CategoryService;
import com.store.shop.util.ApiResponse;
import com.store.shop.util.Common;
import com.store.shop.util.Error;
import com.store.shop.util.Success;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<?> saveCategory(@RequestBody Category category, HttpServletRequest request){
        String requestId = Common.getRequestId();
        String timeStamp = Common.getTimeStamp();
        String endpoint = request.getRequestURI();
        try{
            Category savedCategory = categoryService.saveCategory(category);
            Success<Category> successResponse = ApiResponse.success(timeStamp, requestId, savedCategory, endpoint, HttpStatus.CREATED.value());
            return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
        }catch (DuplicateKeyException ex){
            Error error = ApiResponse.error(timeStamp, requestId, endpoint, HttpStatus.BAD_REQUEST.value(), ex.getMessage());
            return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            // Catch any other unexpected exceptions
            Error error = ApiResponse.error(timeStamp, requestId, endpoint, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<Success<List<Category>>> allCategories(HttpServletRequest request){
        String requestId = Common.getRequestId();
        String timeStamp = Common.getTimeStamp();
        String endpoint = request.getRequestURI();
        List<Category> categoryList = categoryService.findAllCategory();
        Success<List<Category>> successResponse = ApiResponse.success(timeStamp, requestId, categoryList, endpoint, HttpStatus.OK.value());
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @GetMapping("/category")
    public ResponseEntity<?> findByName(@RequestParam String name, HttpServletRequest request) {
        String requestId = Common.getRequestId();
        String timeStamp = Common.getTimeStamp();
        String endpoint = request.getRequestURI();

        try {
            Category category = categoryService.findByCategoryName(name);
            Success<Category> successResponse = ApiResponse.success(timeStamp, requestId, category, endpoint, HttpStatus.OK.value());
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (NotFoundException exception) {
            Error error = ApiResponse.error(timeStamp, requestId, endpoint, HttpStatus.NOT_FOUND.value(), exception.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> findById(@PathVariable String categoryId, HttpServletRequest request) {
        String requestId = Common.getRequestId();
        String timeStamp = Common.getTimeStamp();
        String endpoint = request.getRequestURI();

        try {
            Category category = categoryService.findByCategoryId(categoryId);
            Success<Category> successResponse = ApiResponse.success(timeStamp, requestId, category, endpoint, HttpStatus.OK.value());
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (NotFoundException exception) {
            Error error = ApiResponse.error(timeStamp, requestId, endpoint, HttpStatus.NOT_FOUND.value(), exception.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/category")
    public ResponseEntity<?> updateCategoryInfo(@RequestBody Category category, HttpServletRequest request) {
        String requestId = Common.getRequestId();
        String timeStamp = Common.getTimeStamp();
        String endpoint = request.getRequestURI();

        try {
            Category updatedCategory = categoryService.updateCategoryInfo(category);
            Success<Category> successResponse = ApiResponse.success(timeStamp, requestId, updatedCategory, endpoint, HttpStatus.OK.value());
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (IllegalArgumentException | NotFoundException | DuplicateKeyException ex) {
            Error error = ApiResponse.error(timeStamp, requestId, endpoint, HttpStatus.BAD_REQUEST.value(), ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            // Catch any other unexpected exceptions
            Error error = ApiResponse.error(timeStamp, requestId, endpoint, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
