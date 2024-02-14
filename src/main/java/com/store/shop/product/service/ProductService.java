package com.store.shop.product.service;

import com.store.shop.exception.NotFoundException;
import com.store.shop.product.dto.ProductDto;
import com.store.shop.product.model.Category;
import com.store.shop.product.model.Product;
import com.store.shop.product.repository.ProductRepository;
import com.store.shop.util.Convert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    public ProductDto saveProduct(ProductDto productDto){
        Category category = categoryService.findByCategoryId(productDto.getCategoryId());
        Product product = Convert.dtoToEntity(productDto);
        product.setCategory(category);
        Product dbProduct = productRepository.save(product);
        return Convert.entityToDto(dbProduct);
    }

    public Product findProductById(String productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()){
            throw new NotFoundException("Product not found with id:: "+productId);
        }
        return optionalProduct.get();
    }

    public List<ProductDto> allProducts(){
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(Convert::entityToDto).collect(Collectors.toList());
    }

    public ProductDto updateProductInfo(String productId, ProductDto dto){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()){
            throw new NotFoundException("Product Not Found with id:: "+dto.getId());
        }
        Product product = Convert.dtoToEntity(dto);
        Category category = categoryService.findByCategoryId(dto.getCategoryId());
        product.setCategory(category);
        Product dbProduct = optionalProduct.get();
        BeanUtils.copyProperties(product,dbProduct,nullProperty(product));
        dbProduct.setId(productId);
        logger.info("Updated Product: {}",dbProduct);
        Product savedProduct = productRepository.save(dbProduct);
        return Convert.entityToDto(savedProduct);
    }

    private String[] nullProperty(Object source){
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] descriptors = beanWrapper.getPropertyDescriptors();
        List<String> nullName = new ArrayList<>();
        for(PropertyDescriptor descriptor: descriptors){
            if (beanWrapper.getPropertyValue(descriptor.getName()) ==null){
                nullName.add(descriptor.getName());
            }
        }
        return nullName.toArray(new String[0]);
    }
}
