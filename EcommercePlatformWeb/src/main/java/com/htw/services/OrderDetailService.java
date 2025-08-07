package com.htw.services;

import com.htw.pojo.OrderDetail;
import java.util.List;

/**
 *
 * @author Trung Hau
 */
public interface OrderDetailService {

    List<OrderDetail> getAllOrderDetail(int idOder);

    OrderDetail updateQuantity(OrderDetail orderDetail);
}
