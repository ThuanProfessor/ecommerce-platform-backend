package com.htw.repositories.impl;

import com.htw.pojo.OrderDetail;
import com.htw.repositories.OrderDetailRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Trung Hau
 */
@Repository
@Transactional
public class OrderDetailRepositoryImpl implements OrderDetailRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<OrderDetail> getAllOrderDetail(int idOder) {
        Session s = this.factory.getObject().getCurrentSession();

        Query q = s.createQuery("FROM OrderDetail WHERE orderId.id=: orderId", OrderDetail.class);

        q.setParameter("orderId", idOder);

        return q.getResultList();
    }

    @Override
    public OrderDetail updateQuantity(OrderDetail orderDetail) {
        Session s = this.factory.getObject().getCurrentSession();

        s.merge(orderDetail);
        return orderDetail;
    }
    
    @Override
    public OrderDetail addOrderDetail(OrderDetail od) {
        Session s = factory.getObject().getCurrentSession();
        s.persist(od); 
        return od;
    }
    

}
