package com.htw.repositories;

import java.util.List;
import java.util.Map;

import com.htw.pojo.SaleOrder;
/**
 *
 * @author nguye
 */
public interface OrderRepository {
    List<SaleOrder> getOrders();

    List<SaleOrder> getOrders(Map<String, String> params);
    SaleOrder addOrder(SaleOrder order);
    
    SaleOrder getOrderById(int id);
    void updateOrder(SaleOrder order);
    
    SaleOrder createOrder(SaleOrder order);
    
    SaleOrder updateOrderStatus(int id, String status);
    
    List<SaleOrder> getOrdersByUsername(String username);
    
    
}
