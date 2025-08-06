package com.htw.services.impl;

import com.htw.pojo.OrderDetail;
import com.htw.repositories.OrderDetailRepository;
import com.htw.services.OrderDetailService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Trung Hau
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepo;

    @Override
    public List<OrderDetail> getAllOrderDetail(int idOder) {
        return this.orderDetailRepo.getAllOrderDetail(idOder);
    }

    @Override
    public OrderDetail updateQuantity(OrderDetail orderDetail) {
        return this.orderDetailRepo.updateQuantity(orderDetail);
    }

}
