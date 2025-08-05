package com.htw.services;

import java.util.List;
import java.util.Map;

import com.htw.pojo.Review;

public interface ReviewService {
    List<Review> getReviews();
    
    Review getReviewById(int id);
    
    Review addReview(Review review);
    
    Review updateReview(Review review);
    
    void deleteReview(int id);
    
    List<Review> getProductReviews(int productId);
    
    List<Review> getReviews(Map<String, String> params);
}
