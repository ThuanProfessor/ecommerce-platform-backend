package com.htw.repositories.impl;

import java.util.List;

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

    @Override
    public List<SaleOrder> getOrders() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
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

    

}
