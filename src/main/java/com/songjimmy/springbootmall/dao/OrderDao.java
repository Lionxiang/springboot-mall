package com.songjimmy.springbootmall.dao;

import com.songjimmy.springbootmall.dto.OrderQueryParams;
import com.songjimmy.springbootmall.model.Order;
import com.songjimmy.springbootmall.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemByOrderId(Integer orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);
}
