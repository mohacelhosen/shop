package com.store.shop.util;

import com.store.shop.product.dto.ProductDto;
import com.store.shop.product.model.Product;

public abstract class Convert {

    public static ProductDto entityToDto(Product product){
        ProductDto dto = new ProductDto();

        // id
        if (product.getId() !=null){
            dto.setId(product.getId());
        }

        //images
        if (product.getImages() !=null){
            dto.setImages(product.getImages());
        }

        //brand
        if (product.getBrand() !=null){
            dto.setBrand(product.getBrand());
        }

        //price
        if (product.getPrice() !=null){
            dto.setPrice(product.getPrice());
        }

        //description
        if (product.getDescription() !=null){
            dto.setDescription(product.getDescription());
        }

        // stock
        if (product.getStock() !=null){
            dto.setStock(product.getStock());
        }

        //title
        if (product.getTitle() !=null){
            dto.setTitle(product.getTitle());
        }


        //rating
        if (product.getRating() !=null){
            dto.setRating(product.getRating());
        }


        //subcategories
        if (product.getSubcategories() !=null){
            dto.setSubcategories(product.getSubcategories());
        }


        //discount
        if (product.getDiscountPercentage() !=null){
            dto.setDiscountPercentage(product.getDiscountPercentage());
        }


        //thumbnail
        if (product.getThumbnail() !=null){
            dto.setThumbnail(product.getThumbnail());
        }

        if (product.getCategory() !=null){
            dto.setCategoryId(product.getCategory().getId());
        }

        return dto;
    }

    public static Product dtoToEntity(ProductDto dto){
        Product product = new Product();

        // id
        if (dto.getId() !=null){
            product.setId(dto.getId());
        }

        //images
        if (dto.getImages() !=null){
            product.setImages(dto.getImages());
        }

        //brand
        if (dto.getBrand() !=null){
            product.setBrand(dto.getBrand());
        }

        //price
        if (dto.getPrice() !=null){
            product.setPrice(dto.getPrice());
        }

        //description
        if (dto.getDescription() !=null){
            product.setDescription(dto.getDescription());
        }

        // stock
        if (dto.getStock() !=null){
            product.setStock(dto.getStock());
        }

        //title
        if (dto.getTitle() !=null){
            product.setTitle(dto.getTitle());
        }


        //rating
        if (dto.getRating() !=null){
            product.setRating(dto.getRating());
        }


        //subcategories
        if (dto.getSubcategories() !=null){
            product.setSubcategories(dto.getSubcategories());
        }


        //discount
        if (dto.getDiscountPercentage() !=null){
            product.setDiscountPercentage(dto.getDiscountPercentage());
        }


        //thumbnail
        if (dto.getThumbnail() !=null){
            product.setThumbnail(dto.getThumbnail());
        }

        return product;

    }
}
