package com.store.shop.product.controller;

import com.store.shop.exception.NotFoundException;
import com.store.shop.product.dto.ProductDto;
import com.store.shop.product.model.Product;
import com.store.shop.product.service.ProductService;
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
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDto product, HttpServletRequest request) {
        String requestId = Common.getRequestId();
        String timeStamp = Common.getTimeStamp();
        String endpoint = request.getRequestURI();
        ProductDto saveProduct = productService.saveProduct(product);
        Success<ProductDto> successResponse = ApiResponse.success(timeStamp, requestId, saveProduct, endpoint, HttpStatus.CREATED.value());
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable String productId, HttpServletRequest request) {
        String requestId = Common.getRequestId();
        String timeStamp = Common.getTimeStamp();
        String endpoint = request.getRequestURI();
        try {
            Product product = productService.findProductById(productId);
            Success<Product> successResponse = ApiResponse.success(timeStamp, requestId, product, endpoint, HttpStatus.OK.value());
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (NotFoundException exception) {
            Error error = ApiResponse.error(timeStamp, requestId, endpoint, HttpStatus.NOT_FOUND.value(), exception.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products")
    public ResponseEntity<Success<List<ProductDto>>> allCategories(HttpServletRequest request) {
        String requestId = Common.getRequestId();
        String timeStamp = Common.getTimeStamp();
        String endpoint = request.getRequestURI();
        List<ProductDto> productList = productService.allProducts();
        Success<List<ProductDto>> successResponse = ApiResponse.success(timeStamp, requestId, productList, endpoint, HttpStatus.OK.value());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable String productId, @RequestBody ProductDto productDto, HttpServletRequest request) {
        String requestId = Common.getRequestId();
        String timeStamp = Common.getTimeStamp();
        String endpoint = request.getRequestURI();
        try {
            ProductDto updateProductInfo = productService.updateProductInfo(productId, productDto);
            Success<ProductDto> successResponse = ApiResponse.success(timeStamp, requestId, updateProductInfo, endpoint, HttpStatus.OK.value());
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (NotFoundException exception) {
            Error error = ApiResponse.error(timeStamp, requestId, endpoint, HttpStatus.NOT_FOUND.value(), exception.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

}
