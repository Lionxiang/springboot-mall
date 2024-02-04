package com.songjimmy.springbootmall.service;

import com.songjimmy.springbootmall.dto.CreateOrderRequest;
import com.songjimmy.springbootmall.dto.OrderQueryParams;
import com.songjimmy.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);
}
