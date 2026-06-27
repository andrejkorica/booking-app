package hr.pocetnik.bookingapp.service;

import java.util.List;

import hr.pocetnik.bookingapp.dto.review.ReviewRequest;
import hr.pocetnik.bookingapp.dto.review.ReviewResponse;
import hr.pocetnik.bookingapp.dto.review.ReviewVoteRequest;


public interface ReviewService {
    List<ReviewResponse> getReviews(Long listingId);

    ReviewResponse createReview(Long listingId, String userEmail, ReviewRequest request);

    ReviewResponse voteReview(Long reviewId, String userEmail, ReviewVoteRequest request);
     
}
