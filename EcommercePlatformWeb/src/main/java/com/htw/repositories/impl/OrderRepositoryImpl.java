package com.htw.repositories.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import com.htw.pojo.Order;
import com.htw.repositories.OrderRepository;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Order> getOrders() {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("From Order", Order.class);

        return query.getResultList();
    }

    

}
