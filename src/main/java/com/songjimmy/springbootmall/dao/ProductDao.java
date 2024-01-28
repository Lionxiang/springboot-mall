package com.songjimmy.springbootmall.dao;

import com.songjimmy.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
