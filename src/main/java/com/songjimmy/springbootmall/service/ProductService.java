package com.songjimmy.springbootmall.service;

import com.songjimmy.springbootmall.dto.ProductRequest;
import com.songjimmy.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProduct(Integer productId);
}
