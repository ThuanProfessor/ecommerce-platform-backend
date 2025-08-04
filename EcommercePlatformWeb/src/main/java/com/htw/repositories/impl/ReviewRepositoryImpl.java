package com.htw.repositories.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.Transactional;

import com.htw.pojo.Review;
import com.htw.repositories.ReviewRepository;

import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ReviewRepositoryImpl implements ReviewRepository{

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Review> getReviews() {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Review", Review.class);
        return query.getResultList();
    }

    @Override
    public Review getReviewById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(Review.class, id);
    }

    @Override
    public Review addReview(Review review) {
        Session session = this.factory.getObject().getCurrentSession();
        review.setCreatedDate(new Date());
        session.persist(review);
        return review;
    }

    @Override
    public Review updateReview(Review review) {
        Session session = this.factory.getObject().getCurrentSession();
        session.merge(review);
        return review;
    }

    
    @Override
    public void deleteReview(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Review review = this.getReviewById(id);
        session.remove(review);
    }

    //lấy danh sách review theo product ÍD
    @Override
    public List<Review> getProductReviews(int productId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Review WHERE productId.id = :productId", Review.class);
        query.setParameter("productId", productId);
        return query.getResultList();
    }




    @Override
    public List<Review> getReviews(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Review", Review.class);
        return query.getResultList();
    }
}
