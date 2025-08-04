package com.htw.repositories.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import com.htw.pojo.SaleOrder;
import com.htw.repositories.OrderRepository;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    // @Override
    // public List<SaleOrder> getOrders() {
    //     throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    // }
//    @Autowired
//    private LocalSessionFactoryBean factory;
//
//    @Override
//    public List<SaleOrder> getOrders() {
//        Session session = this.factory.getObject().getCurrentSession();
//        Query query = session.createQuery("From Order", SaleOrder.class);
//
//        return query.getResultList();
//    }

    @Override
    public List<SaleOrder> getOrders() {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM SaleOrder", SaleOrder.class);
        return query.getResultList();
    }

    @Override
    public List<SaleOrder> getOrders(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM SaleOrder", SaleOrder.class);
        return query.getResultList();
    }

    @Override
    public SaleOrder getOrderById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(SaleOrder.class, id);
    }

    @Override
    public SaleOrder createOrder(SaleOrder order) {
        Session session = this.factory.getObject().getCurrentSession();
        session.persist(order);
        return order;
    }

    @Override
    public SaleOrder updateOrderStatus(int id, String status) {
        Session session = this.factory.getObject().getCurrentSession();
        SaleOrder order = this.getOrderById(id);
        // Cập nhật trạng thái đơn hàng
        return order;
    }

    @Override
    public List<SaleOrder> getOrdersByUsername(String username) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM SaleOrder WHERE userId.username = :username", SaleOrder.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    //Nhaps
   
    

}
