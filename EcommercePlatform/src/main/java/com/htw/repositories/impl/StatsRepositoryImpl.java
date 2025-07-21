package com.htw.repositories.impl;


import com.htw.ecommerceplatform.HibernateConfigs;
import com.htw.pojo.Order;
import com.htw.pojo.OrderDetail;
import com.htw.pojo.Product;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

import java.util.List;
import java.util.Objects;

public class StatsRepositoryImpl {
    public List<Objects[]> statsDataOverview(String quater, int i) {
        try (Session s = HibernateConfigs.getFACTORY().openSession()) {
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Objects[]> q = b.createQuery(Objects[].class);

            Root r = q.from(OrderDetail.class);
            Join<OrderDetail, Product> join = r.join("productId");

            q.multiselect(join.get("id"), join.get("name"), b.sum(b.prod(r.get("quantity"), r.get("unitPrice"))));

            q.groupBy(join.get("id"));

            Query query = s.createQuery(q);
            return query.getResultList();

        }
    }

    public List<Object[]> statsRevenueByTime(String time, int year) {
        try ( Session s = HibernateConfigs.getFACTORY().openSession()) {
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
            Root r = q.from(OrderDetail.class);
            Join<OrderDetail, Order> join = r.join("orderId");

            q.multiselect(b.function(time, Integer.class, join.get("createdDate")),
                    b.sum(b.prod(r.get("quantity"), r.get("unitPrice"))));
            q.where(b.equal(b.function("YEAR", Integer.class, join.get("createdDate")), year));
            q.groupBy(b.function(time, Integer.class, join.get("createdDate")));

            Query query = s.createQuery(q);
            return query.getResultList();
        }
    }
}
