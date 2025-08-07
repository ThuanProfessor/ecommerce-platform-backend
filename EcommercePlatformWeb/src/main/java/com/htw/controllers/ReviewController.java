package com.htw.controllers;

import com.htw.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Trung Hau
 */
@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviews")
    public String viewAllReview(Model model) {
        model.addAttribute("reviews", this.reviewService.getReviews());
        return "review-list";
    }
}
