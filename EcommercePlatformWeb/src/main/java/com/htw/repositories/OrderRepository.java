package com.htw.repositories;

import java.util.List;

import com.htw.pojo.Order;
/**
 *
 * @author nguye
 */
public interface OrderRepository {
    List<Order> getOrders();
}
