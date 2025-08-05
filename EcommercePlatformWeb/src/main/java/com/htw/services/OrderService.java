/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.htw.services;

import com.htw.pojo.SaleOrder;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nguye
 */
public interface OrderService {
    List<SaleOrder> getOrders();

    List<SaleOrder> getOrders(Map<String, String> params);
    
    SaleOrder getOrderById(int id);
    
    SaleOrder createOrder(Map<String, Object> orderData);
    List<SaleOrder> getMyOrders();
     

    
}
