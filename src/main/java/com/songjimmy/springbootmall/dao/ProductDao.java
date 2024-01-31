package com.songjimmy.springbootmall.dao;

import com.songjimmy.springbootmall.dto.ProductQueryParams;
import com.songjimmy.springbootmall.dto.ProductRequest;
import com.songjimmy.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProduct(Integer productId);

    Integer countProduct(ProductQueryParams productQueryParams);
}
