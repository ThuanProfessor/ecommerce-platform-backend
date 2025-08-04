package com.htw.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.htw.pojo.SaleOrder;
import com.htw.pojo.User;
import com.htw.repositories.OrderRepository;
import com.htw.services.OrderService;
import com.htw.services.UserService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;


    @Override
    public List<SaleOrder> getOrders() {
        return this.orderRepository.getOrders();

    }

    @Override
    public List<SaleOrder> getOrders(Map<String, String> params) {
        return this.orderRepository.getOrders(params);
    }

    @Override
    public SaleOrder getOrderById(int id) {
        return this.orderRepository.getOrderById(id);
    }

    @Override
    public SaleOrder createOrder(Map<String, Object> orderData) {
        SaleOrder order = new SaleOrder();
        order.setCreatedDate(new Date());
        order.setNote((String) orderData.get("note"));
        
        // Lấy user hiện tại
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUserByUsername(username);
        order.setUserId(user);
        
        return this.orderRepository.createOrder(order);
    }

    @Override
    public List<SaleOrder> getMyOrders() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return this.orderRepository.getOrdersByUsername(username);
    }

  
}
