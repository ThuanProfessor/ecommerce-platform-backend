package com.htw.repositories.impl;

import com.htw.pojo.OrderDetail;
import com.htw.pojo.Product;
import com.htw.pojo.SaleOrder;
import com.htw.pojo.Category;
import com.htw.pojo.User;
import com.htw.pojo.Store;
import com.htw.repositories.StatsRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import java.util.List;
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
        Root<OrderDetail> root = q.from(OrderDetail.class);
        Join<OrderDetail, Product> join = root.join("productId", JoinType.RIGHT);
        q.multiselect(join.get("id"), join.get("name"),
                b.sum(b.prod(root.get("unitPrice"),
                        b.toLong(root.get("quantity")))));
        q.groupBy(join.get("id"), join.get("name"));
        q.orderBy(b.desc(b.sum(b.prod(root.get("unitPrice"),
                b.toLong(root.get("quantity"))))));

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
public List<Object[]> statsRevenueByTime(String time, int year) {
    Session s = this.factory.getObject().getCurrentSession();
    CriteriaBuilder b = s.getCriteriaBuilder();
    CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
    Root<OrderDetail> root = q.from(OrderDetail.class);
    Join<OrderDetail, SaleOrder> join = root.join("orderId", JoinType.INNER);

    String functionName = "month".equalsIgnoreCase(time) ? "MONTH" : "QUARTER";

    q.multiselect(
        b.function(functionName, Integer.class, join.get("createdDate")),
        b.sum(b.prod(root.get("unitPrice"), root.get("quantity")))
    );
    q.where(b.equal(b.function("YEAR", Integer.class, join.get("createdDate")), year));
    q.groupBy(b.function(functionName, Integer.class, join.get("createdDate")));
    q.orderBy(b.asc(b.function(functionName, Integer.class, join.get("createdDate"))));

    Query query = s.createQuery(q);
    return query.getResultList();
}
    @Override
    public List<Object[]> countOrdersByMonth() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<SaleOrder> root = q.from(SaleOrder.class);

        q.multiselect(b.function("MONTH", Integer.class, root.get("createdDate")),
                b.count(root.get("id")));
        q.where(b.equal(b.function("YEAR", Integer.class, root.get("createdDate")),
                b.function("YEAR", Integer.class, b.currentDate())));
        q.groupBy(b.function("MONTH", Integer.class, root.get("createdDate")));
        q.orderBy(b.asc(b.function("MONTH", Integer.class, root.get("createdDate"))));

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public List<Object[]> countProductsByCategory() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<Category> root = q.from(Category.class);
        Join<Category, Product> join = root.join("productSet", JoinType.LEFT);

        q.multiselect(root.get("name"), b.count(join.get("id")));
        q.groupBy(root.get("id"), root.get("name"));
        q.orderBy(b.desc(b.count(join.get("id"))));

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public List<Object[]> countUsersByRole() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<User> root = q.from(User.class);

        q.multiselect(root.get("role"), b.count(root.get("id")));
        q.groupBy(root.get("role"));
        q.orderBy(b.desc(b.count(root.get("id"))));

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public long countUsers() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<User> root = q.from(User.class);

        q.select(b.count(root.get("id")));
        Query query = s.createQuery(q);
        Object result = query.getSingleResult();
        return result instanceof Number ? ((Number) result).longValue() : 0L;
    }

    @Override
    public long countProducts() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<Product> root = q.from(Product.class);

        q.select(b.count(root.get("id")));
        Query query = s.createQuery(q);
        Object result = query.getSingleResult();
        return result instanceof Number ? ((Number) result).longValue() : 0L;
    }

    @Override
    public long countStores() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<Store> root = q.from(Store.class);

        q.select(b.count(root.get("id")));
        Query query = s.createQuery(q);
        Object result = query.getSingleResult();
        return result instanceof Number ? ((Number) result).longValue() : 0L;
    }

    @Override
    public long countOrders() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<SaleOrder> root = q.from(SaleOrder.class);

        q.select(b.count(root.get("id")));
        Query query = s.createQuery(q);
        Object result = query.getSingleResult();
        return result instanceof Number ? ((Number) result).longValue() : 0L;
    }

    @Override
    public long countCategories() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<Category> root = q.from(Category.class);

        q.select(b.count(root.get("id")));
        Query query = s.createQuery(q);
        Object result = query.getSingleResult();
        return result instanceof Number ? ((Number) result).longValue() : 0L;
    }
}
