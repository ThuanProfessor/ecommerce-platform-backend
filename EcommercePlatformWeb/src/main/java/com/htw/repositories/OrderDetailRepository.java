package com.htw.repositories;

import com.htw.pojo.OrderDetail;
import java.util.List;

/**
 *
 * @author Trung Hau
 */
public interface OrderDetailRepository {

    List<OrderDetail> getAllOrderDetail(int idOder);

    OrderDetail updateQuantity(OrderDetail orderDetail);
    
    OrderDetail addOrderDetail(OrderDetail od);

}
