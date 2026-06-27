package hr.pocetnik.bookingapp.controller;

import hr.pocetnik.bookingapp.dto.review.ReviewRequest;
import hr.pocetnik.bookingapp.dto.review.ReviewResponse;
import hr.pocetnik.bookingapp.dto.review.ReviewVoteRequest;
import hr.pocetnik.bookingapp.service.ReviewService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listings/{listingId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewResponse> getReviews(@PathVariable Long listingId) {
        return reviewService.getReviews(listingId);
    }

    @PostMapping
    public ReviewResponse createReview(
            @PathVariable Long listingId,
            @RequestBody ReviewRequest request,
            Authentication authentication
    ) {
        return reviewService.createReview(
                listingId,
                authentication.getName(),
                request
        );
    }

    @PostMapping("/{reviewId}/vote")
    public ReviewResponse voteReview(
            @PathVariable Long listingId,
            @PathVariable Long reviewId,
            @RequestBody ReviewVoteRequest request,
            Authentication authentication
    ) {
        return reviewService.voteReview(
                reviewId,
                authentication.getName(),
                request
        );
    }
}