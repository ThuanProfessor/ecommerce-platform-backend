package com.htw.repositories.impl;

import com.htw.pojo.Product;
import com.htw.repositories.ProductRepository;
import com.htw.repositories.Review;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author nguye
 */
@Repository
@Transactional
public class ProductRepositotyImpl implements ProductRepository {

    // Phân trang: 20 sản phẩm/trang
    private static final int PAGE_SIZE = 20;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Product> getProducts(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Product> q = b.createQuery(Product.class);

        Root root = q.from(Product.class);
        q.select(root);

        //Filter
        if (params != null) {

            System.err.println("Params nè" + params);

            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));

            }

            String fromPrice = params.get("fromPrice");
            if (fromPrice != null && !fromPrice.isEmpty()) {
                predicates.add(b.greaterThanOrEqualTo(root.get("price"), fromPrice));
            }

            String cateId = params.get("cateId");
            if (cateId != null && !cateId.isEmpty()) {
                predicates.add(b.equal(root.get("category").as(Integer.class), cateId));
            }

            q.where(predicates.toArray(Predicate[]::new));

            //sort
            q.orderBy(b.desc(root.get(params.getOrDefault("sortBy", "id"))));
        }

        Query query = session.createQuery(q);

        //Page
        if (params != null) {
            String page = params.get("page");
            if (page != null) {
                int p = Integer.parseInt(page);
                int start = (p - 1) * PAGE_SIZE;

                query.setFirstResult(start);
                query.setMaxResults(PAGE_SIZE);
            }
        }

        return query.getResultList();
    }

    @Override
    public Product getProductById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(Product.class, id);
    }

    @Override
    public Product addOrUpdateProduct(Product product) {
        Session session = this.factory.getObject().getCurrentSession();
        if (product.getId() == null) {
            session.persist(product);
        } else {
            session.merge(product);
        }

        return product;
    }

    
    @Override
    public void deleleProduct(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Product product = this.getProductById(id);
        session.remove(product);
    }

    @Override
    public List<Product> getProductsByIds(List<Integer> productIds) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Product WHERE id IN :productIds", Product.class);
        query.setParameter("productIds", productIds);
        return query.getResultList();
    }

    

    @Override
    public List<Product> getProductsByStore(int storeId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Product WHERE storeId.id = :storeId", Product.class);
        query.setParameter("storeId", storeId);
        return query.getResultList();
    }

  

    

}
