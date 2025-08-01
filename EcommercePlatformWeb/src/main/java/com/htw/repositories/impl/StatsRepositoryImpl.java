package com.htw.repositories.impl;

import com.htw.pojo.OrderDetail;
import com.htw.pojo.Product;
import com.htw.repositories.StatsRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Locale.Category;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Object[]> statsRevenueByProduct() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root root = q.from(OrderDetail.class);
        Join<OrderDetail, Product> join = root.join("productId", JoinType.RIGHT);

        q.multiselect(join.get("id"), join.get("name"), 
                      b.sum(b.prod(root.get("quantity"), root.get("unitPrice"))));
        q.groupBy(join.get("id"), join.get("name"));
        q.orderBy(b.desc(b.sum(b.prod(root.get("quantity"), root.get("unitPrice")))));
        
        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public List<Object[]> statsCategory() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<Product> root = q.from(Product.class);
        Join<Product, Category> join = root.join("category", JoinType.INNER);

        q.multiselect(
            join.get("id"),
            join.get("name"),
            b.count(root.get("id"))
        );

        q.groupBy(join.get("id"), join.get("name"));
        q.orderBy(b.desc((b.count(root.get("id")))));
        
        Query query = s.createQuery(q);

        return query.getResultList();
    }


    
}
