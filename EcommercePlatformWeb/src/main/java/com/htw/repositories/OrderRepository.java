package com.htw.repositories;

import java.util.List;

import com.htw.pojo.SaleOrder;
/**
 *
 * @author nguye
 */
public interface OrderRepository {
    List<SaleOrder> getOrders();
}
