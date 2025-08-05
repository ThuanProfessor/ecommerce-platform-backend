package com.htw.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.htw.pojo.Review;
import com.htw.services.ReviewService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> list(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.reviewService.getReviews(params), HttpStatus.OK);
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> retrieve(@PathVariable(value = "reviewId") int id) {
        return new ResponseEntity<>(this.reviewService.getReviewById(id), HttpStatus.OK);
    }

    @PostMapping("/reviews")
    public ResponseEntity<Review> create(@RequestBody Review review) {
        return new ResponseEntity<>(this.reviewService.addReview(review), HttpStatus.CREATED);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> update(@PathVariable(value = "reviewId") int id, @RequestBody Review review) {
        review.setId(id);
        return new ResponseEntity<>(this.reviewService.updateReview(review), HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable(value = "reviewId") int id) {
        this.reviewService.deleteReview(id);
    }

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable(value = "productId") int productId) {
        return new ResponseEntity<>(this.reviewService.getProductReviews(productId), HttpStatus.OK);
    }
}
