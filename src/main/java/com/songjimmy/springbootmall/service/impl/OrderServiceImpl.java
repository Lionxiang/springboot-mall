package com.songjimmy.springbootmall.service.impl;

import com.songjimmy.springbootmall.dao.OrderDao;
import com.songjimmy.springbootmall.dao.ProductDao;
import com.songjimmy.springbootmall.dto.BuyItem;
import com.songjimmy.springbootmall.dto.CreateOrderRequest;
import com.songjimmy.springbootmall.model.OrderItem;
import com.songjimmy.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.songjimmy.springbootmall.model.Product;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            // 計算totalAmount
            Product product = productDao.getProductById(buyItem.getProductId());
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount += amount;
            // 轉換BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        // 創建訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        // 創建訂單明細
        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
