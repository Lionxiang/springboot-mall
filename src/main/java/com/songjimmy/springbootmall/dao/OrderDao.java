package com.songjimmy.springbootmall.dao;

import com.songjimmy.springbootmall.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
