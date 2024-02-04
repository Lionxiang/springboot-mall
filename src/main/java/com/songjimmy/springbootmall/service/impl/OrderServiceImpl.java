package com.songjimmy.springbootmall.service.impl;

import com.songjimmy.springbootmall.dao.OrderDao;
import com.songjimmy.springbootmall.dao.ProductDao;
import com.songjimmy.springbootmall.dao.UserDao;
import com.songjimmy.springbootmall.dto.BuyItem;
import com.songjimmy.springbootmall.dto.CreateOrderRequest;
import com.songjimmy.springbootmall.dto.OrderQueryParams;
import com.songjimmy.springbootmall.model.Order;
import com.songjimmy.springbootmall.model.OrderItem;
import com.songjimmy.springbootmall.model.Product;
import com.songjimmy.springbootmall.model.User;
import com.songjimmy.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        // 先確認此用戶是否存在
        User user = userDao.getUserById(userId);

        if(user == null){
            log.warn("此用戶 {} 不存在，無法新增訂單", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());
            if(product == null){
                log.warn("此商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }else if(product.getStock() < buyItem.getQuantity()){
                log.warn("此商品 {} 庫存不足，無法購買。剩餘數量{}，預購買數量{}",
                        product.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            // 更新庫存資料
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

            // 計算totalAmount
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

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemByOrderId(orderId);
        order.setOrderItemList(orderItemList);

        return order;
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);

        for(Order order : orderList){
            List<OrderItem> orderItemList = orderDao.getOrderItemByOrderId(order.getOrderId());
            order.setOrderItemList(orderItemList);
        }

        return orderList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
    }
}
