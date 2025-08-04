/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.htw.repositories.impl;

import com.htw.pojo.Store;
import com.htw.repositories.StoreRepository;
import jakarta.transaction.Transactional;
import java.util.List;
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
    public Store getStoreById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Store.class, id);
    }

    @Override
    public Store addOrUpdateStore(Store store) {
        Session s = this.factory.getObject().getCurrentSession();
        if (store.getId() == null) {
            s.persist(store);
        } else {
            s.merge(store);
        }

        return store;
    }

}
