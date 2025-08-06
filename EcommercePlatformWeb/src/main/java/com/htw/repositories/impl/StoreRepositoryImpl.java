/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.htw.repositories.impl;

import com.htw.pojo.Store;
import com.htw.pojo.Product;
import com.htw.repositories.StoreRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
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
    public Store getStoreById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Store.class, id);
    }

    @Override
    public Store addOrUpdateStore(Store store) {
        Session s = this.factory.getObject().getCurrentSession();
        if (store.getId() == null) {
            store.setCreatedDate(new Date());
            s.persist(store);
        } else {
            s.merge(store);
        }

        return store;
    }

    @Override
    public List<Store> getStores(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Store> q = b.createQuery(Store.class);
        Root root = q.from(Store.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
            }

            q.where(predicates.toArray(Predicate[]::new));
        }

        Query query = session.createQuery(q);

        return query.getResultList();
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
        List<Store> stores = query.getResultList();
        if (stores != null && !stores.isEmpty()) {
            return stores.get(0);
        }
        return null;
    }

    @Override
    public List<Store> getStores() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Store", Store.class);

        return q.getResultList();
    }
}
