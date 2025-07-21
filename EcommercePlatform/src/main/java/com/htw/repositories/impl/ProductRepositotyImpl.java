package com.htw.repositories.impl;

import com.htw.ecommerceplatform.HibernateConfigs;
import com.htw.pojo.Product;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nguye
 */
public class ProductRepositotyImpl {
    private static final int PAGE_SIZE = 20;

    public List<Product> getProducts(Map<String, String> params){
         try(Session s = HibernateConfigs.getFACTORY().openSession()){
             CriteriaBuilder b = s.getCriteriaBuilder();
             CriteriaQuery<Product> q = b.createQuery(Product.class);
             
             Root root = q.from(Product.class);
             q.select(root);
             
             
             //Filter
             if(params != null){
                 
                 List<Predicate> predicates = new ArrayList<>();
                 
                 String kw = params.get("kw");
                 if(kw!=null && !kw.isEmpty()){
                     predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
                     
                 }
                 
                 String fromPrice = params.get("fromPrice");
                 if(fromPrice != null && !fromPrice.isEmpty()){
                     predicates.add(b.greaterThanOrEqualTo(root.get("price"), fromPrice));
                 }

                 String cateId = params.get("cateId");
                 if(cateId != null && !cateId.isEmpty()){
                     predicates.add(b.equal(root.get("category").as(Integer.class), cateId));
                 }
                 
                 q.where(predicates.toArray(Predicate[]::new));

                 //sort
                 q.orderBy(b.desc(root.get(params.getOrDefault("sortBy", "id"))));
             }
             
             Query query = s.createQuery(q);

             //Page
             if(params != null){
                 String page = params.get("page");
                 if(page != null){
                     int p = Integer.parseInt(page);
                     int start = (p - 1)*PAGE_SIZE;

                     query.setFirstResult(start);
                     query.setMaxResults(PAGE_SIZE);
                 }
             }

             return query.getResultList();
         }
    }
}
