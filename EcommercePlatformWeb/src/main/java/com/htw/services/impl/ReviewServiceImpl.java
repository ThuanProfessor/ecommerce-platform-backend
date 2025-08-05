package com.htw.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.htw.pojo.Review;
import com.htw.repositories.ReviewRepository;
import com.htw.services.ReviewService;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<Review> getReviews() {
        return this.reviewRepository.getReviews();
    }

    @Override
    public Review getReviewById(int id) {
        return this.reviewRepository.getReviewById(id);
    }

    @Override
    public Review addReview(Review review) {
        return this.reviewRepository.addReview(review);
    }

    @Override
    public Review updateReview(Review review) {
        return this.reviewRepository.updateReview(review);
    }

    @Override
    public void deleteReview(int id) {
        this.reviewRepository.deleteReview(id);
    }
    //
    
    @Override
    public List<Review> getProductReviews(int productId) {
        return this.reviewRepository.getProductReviews(productId);
    }

    @Override
    public List<Review> getReviews(Map<String, String> params) {
        return this.reviewRepository.getReviews(params);
    }

}
