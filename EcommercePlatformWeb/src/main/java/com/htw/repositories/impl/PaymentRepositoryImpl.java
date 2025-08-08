package com.htw.repositories.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Session;


import jakarta.persistence.Query;

import com.htw.pojo.Payment;
import com.htw.repositories.PaymentRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public class PaymentRepositoryImpl implements PaymentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    
    @Override
    public List<Payment> getPayments() {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Payment", Payment.class);
        return query.getResultList();
    }

    @Override
    public Payment getPaymentById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(Payment.class, id);
    }

    @Override
    public Payment addPayment(Payment payment) {
        Session session = this.factory.getObject().getCurrentSession();
        payment.setCreatedDate(new Date());
        session.persist(payment);
        return payment;
    }



    @Override
    public Payment updatePayment(Payment payment) {
        Session session = this.factory.getObject().getCurrentSession();
        session.merge(payment);
        return payment;
    }



    @Override
    public void deletePayment(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Payment payment = this.getPaymentById(id);
        session.remove(payment);
    }

    @Override
    public List<Payment> getPayments(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Payment", Payment.class);
        return query.getResultList();
    }

    
    
    @Override
    public List<Payment> getPaymentHistory() {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Payment ORDER BY createdDate DESC", Payment.class);
        return query.getResultList();
    }
    
    @Override
    public Payment save(Payment payment) {
        Session session = this.factory.getObject().getCurrentSession();
        if (payment.getId() == null) {
            session.persist(payment);
        } else {
            session.merge(payment);
        }
        return payment;
    }
    
    
    @Override
    public Payment findByOrderCode(String orderCode) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Payment WHERE orderCode = :orderCode", Payment.class);
        query.setParameter("orderCode", orderCode);
        List<Payment> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
