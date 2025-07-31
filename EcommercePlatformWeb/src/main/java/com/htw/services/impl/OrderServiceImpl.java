package com.htw.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htw.pojo.Order;
import com.htw.repositories.OrderRepository;
import com.htw.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getOrders() {
        return this.orderRepository.getOrders();

    }

}
