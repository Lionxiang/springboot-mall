package com.songjimmy.springbootmall.service;

import com.songjimmy.springbootmall.dto.CreateOrderRequest;
import com.songjimmy.springbootmall.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
