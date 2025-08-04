/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.htw.repositories.impl;


import com.htw.pojo.Store;
import com.htw.pojo.Product;
import com.htw.repositories.StoreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nguye
 */
@Repository
@Transactional
public class StoreRepositoryImpl implements StoreRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Store> getStores() {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Store", Store.class);

        return query.getResultList();
    }

     @Override
    public List<Store> getStores(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Store", Store.class);
        return query.getResultList();
    }

    @Override
    public Store getStoreById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(Store.class, id);
    }

    @Override
    public Store createStore(Store store) {
        Session session = this.factory.getObject().getCurrentSession();
        session.persist(store);
        return store;
    }

    @Override
    public void deleteStore(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Store store = this.getStoreById(id);
        session.remove(store);
    }

    @Override
    public List<Product> getStoreProducts(int storeId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Product WHERE storeId.id = :storeId", Product.class);
        query.setParameter("storeId", storeId);
        return query.getResultList();
    }

    @Override
    public Store getStoreByUsername(String username) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Store s WHERE s.userId.username = :username", Store.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    
}
