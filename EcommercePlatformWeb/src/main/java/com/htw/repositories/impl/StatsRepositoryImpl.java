package com.htw.repositories.impl;

import com.htw.pojo.OrderDetail;
import com.htw.pojo.Product;
import com.htw.pojo.SaleOrder;
import com.htw.pojo.Store;
import com.htw.repositories.StatsRepository;
import com.htw.repositories.StoreRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
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

    @Autowired
    private StoreRepository storeRepo;

    @Override
    public List<Object[]> statsRevenueByProduct() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root root = q.from(OrderDetail.class);
        Join<OrderDetail, Product> join = root.join("productId", JoinType.RIGHT);

        q.multiselect(join.get("id"), join.get("name"), b.sum(b.prod(root.get("quantity"), root.get("unitPrice"))));
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

        q.multiselect(join.get("id"), join.get("name"), b.count(root.get("id")));

        q.groupBy(join.get("id"), join.get("name"));
        q.orderBy(b.desc((b.count(root.get("id")))));

        Query query = s.createQuery(q);

        return query.getResultList();
    }

    @Override
    public List<Object[]> statsRevenueByStore() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root root = q.from(OrderDetail.class);
        Join<OrderDetail, Product> productJoin = root.join("productId", JoinType.INNER);
        Join<Product, Store> storeJoin = productJoin.join("storeId");

        q.multiselect(storeJoin.get("id"), storeJoin.get("name"), b.sum(b.prod(root.get("unitPrice"), root.get("quantity"))));

        q.groupBy(storeJoin.get("id"), storeJoin.get("name"));
        Query query = s.createQuery(q);

        return query.getResultList();

    }

    @Override
    public List<Object[]> statsRevenueByMonth() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<OrderDetail> root = q.from(OrderDetail.class);
        Join<OrderDetail, com.htw.pojo.SaleOrder> orderJoin = root.join("orderId");

        Expression<Integer> monthExp = b.function("month", Integer.class, orderJoin.get("createdDate"));
        Expression<Integer> yearExp = b.function("year", Integer.class, orderJoin.get("createdDate"));

        q.multiselect(monthExp, yearExp, b.sum(b.prod(root.get("unitPrice"), root.get("quantity"))));
        q.groupBy(yearExp, monthExp);
        q.orderBy(b.asc(yearExp), b.asc(monthExp));

        return s.createQuery(q).getResultList();
    }

    @Override
    public List<Object[]> statsRevenueByQuarter() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<OrderDetail> root = q.from(OrderDetail.class);
        Join<OrderDetail, com.htw.pojo.SaleOrder> orderJoin = root.join("orderId");

        Expression<Integer> quarterExp = b.function("quarter", Integer.class, orderJoin.get("createdDate"));
        Expression<Integer> yearExp = b.function("year", Integer.class, orderJoin.get("createdDate"));

        q.multiselect(quarterExp, yearExp, b.sum(b.prod(root.get("unitPrice"), root.get("quantity"))));
        q.groupBy(yearExp, quarterExp);
        q.orderBy(b.asc(yearExp), b.asc(quarterExp));

        return s.createQuery(q).getResultList();
    }

    @Override
    public List<Object[]> statsRevenueByYear() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<OrderDetail> root = q.from(OrderDetail.class);
        Join<OrderDetail, com.htw.pojo.SaleOrder> orderJoin = root.join("orderId");

        Expression<Integer> yearExp = b.function("year", Integer.class, orderJoin.get("createdDate"));

        q.multiselect(yearExp, b.sum(b.prod(root.get("unitPrice"), root.get("quantity"))));
        q.groupBy(yearExp);
        q.orderBy(b.asc(yearExp));

        return s.createQuery(q).getResultList();
    }

    @Override
    public List<Object[]> statsRevenueAllStoreByMonth(Integer month) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<OrderDetail> root = q.from(OrderDetail.class);
        Join<OrderDetail, Product> productJoin = root.join("productId");
        Join<Product, Store> storeJoin = productJoin.join("storeId");
        Join<OrderDetail, SaleOrder> orderJoin = root.join("orderId");

        Expression<Integer> orderMonth = b.function("month", Integer.class, orderJoin.get("createdDate"));
        Expression<Integer> orderYear = b.function("year", Integer.class, orderJoin.get("createdDate"));

        Predicate predicate = b.conjunction();
        if (month != null) {
            predicate = b.equal(orderMonth, month);
        }

        q.multiselect(
                orderYear,
                orderMonth,
                storeJoin.get("id"),
                storeJoin.get("name"),
                b.sum(b.prod(root.get("unitPrice"), root.get("quantity")))
        );
        q.where(predicate);
        q.groupBy(orderMonth, orderYear, storeJoin.get("id"), storeJoin.get("name"));
        q.orderBy(b.asc(orderMonth), b.asc(orderYear));

        return s.createQuery(q).getResultList();
    }

    @Override
    public List<Object[]> statsRevenueAllStoreByQuarterAndYear(Integer quarter, Integer year) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<OrderDetail> root = q.from(OrderDetail.class);
        Join<OrderDetail, Product> productJoin = root.join("productId");
        Join<Product, Store> storeJoin = productJoin.join("storeId");
        Join<OrderDetail, SaleOrder> orderJoin = root.join("orderId");

        Expression<Integer> quarterExp = b.function("quarter", Integer.class, orderJoin.get("createdDate"));
        Expression<Integer> yearExp = b.function("year", Integer.class, orderJoin.get("createdDate"));

        Predicate predicate = b.conjunction();
        if (quarter != null) {
            predicate = b.and(predicate, b.equal(quarterExp, quarter));
        }
        if (year != null) {
            predicate = b.and(predicate, b.equal(yearExp, year));
        }

        q.multiselect(quarterExp,
                yearExp,
                storeJoin.get("name"),
                b.sum(b.prod(root.get("unitPrice"), root.get("quantity"))));
        q.where(predicate);
        q.groupBy(yearExp, quarterExp,
                storeJoin.get("id"),
                storeJoin.get("name"));
        q.orderBy(b.asc(yearExp), b.asc(quarterExp));

        return s.createQuery(q).getResultList();
    }

    @Override
    public List<Object[]> statsRevenueByStore(String username) {
        Store store = this.storeRepo.getStoreByUsername(username);

        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root root = q.from(OrderDetail.class);
        Join<OrderDetail, Product> productJoin = root.join("productId", JoinType.INNER);
        Join<Product, Store> storeJoin = productJoin.join("storeId");

        q.multiselect(storeJoin.get("id"),
                storeJoin.get("name"),
                b.sum(b.prod(root.get("unitPrice"), root.get("quantity"))));
        q.where(b.equal(storeJoin.get("id"), store.getId()));
        q.groupBy(storeJoin.get("id"), storeJoin.get("name"));
        Query query = s.createQuery(q);

        return query.getResultList();

    }

}
