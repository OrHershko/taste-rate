package com.tasterate.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tasterate.model.Review;
import com.tasterate.repository.RestaurantRepository;
import com.tasterate.repository.ReviewRepository;

@Service
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    
    @Autowired
    public ReviewService(ReviewRepository reviewRepository, RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
    }
    
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
    
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }
    
    public List<Review> getReviewsByRestaurantId(Long restaurantId) {
        return reviewRepository.findByRestaurantId(restaurantId);
    }
    
    public List<Review> getReviewsByUserId(String userId) {
        return reviewRepository.findByUserId(userId);
    }
    
    @Transactional
    public Optional<Review> addReviewToRestaurant(Long restaurantId, Review review, String userId) {
        return restaurantRepository.findById(restaurantId)
            .map(restaurant -> {
                review.setRestaurant(restaurant);
                review.setUserId(userId);
                review.setCreatedAt(LocalDateTime.now());
                return reviewRepository.save(review);
            });
    }
    
    @Transactional
    public boolean updateReview(Long id, Review reviewDetails, String userId) {
        return reviewRepository.findById(id)
            .map(review -> {
                // Only allow updates if the user owns this review
                if (review.getUserId().equals(userId)) {
                    review.setRating(reviewDetails.getRating());
                    review.setComment(reviewDetails.getComment());
                    reviewRepository.save(review);
                    return true;
                }
                return false;
            })
            .orElse(false);
    }
    
    @Transactional
    public boolean deleteReview(Long id, String userId) {
        return reviewRepository.findById(id)
            .map(review -> {
                // Only allow deletion if the user owns this review
                if (review.getUserId().equals(userId)) {
                    reviewRepository.deleteById(id);
                    return true;
                }
                return false;
            })
            .orElse(false);
    }
}