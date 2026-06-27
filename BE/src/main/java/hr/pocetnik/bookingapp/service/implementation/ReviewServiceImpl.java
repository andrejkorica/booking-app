package hr.pocetnik.bookingapp.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import hr.pocetnik.bookingapp.dto.review.ReviewRequest;
import hr.pocetnik.bookingapp.dto.review.ReviewResponse;
import hr.pocetnik.bookingapp.dto.review.ReviewVoteRequest;
import hr.pocetnik.bookingapp.enums.ReviewVoteType;
import hr.pocetnik.bookingapp.model.ListingEntity;
import hr.pocetnik.bookingapp.model.ReviewEntity;
import hr.pocetnik.bookingapp.model.ReviewVoteEntity;
import hr.pocetnik.bookingapp.model.UserEntity;
import hr.pocetnik.bookingapp.repository.ListingRepository;
import hr.pocetnik.bookingapp.repository.ReviewRepository;
import hr.pocetnik.bookingapp.repository.ReviewVoteRepository;
import hr.pocetnik.bookingapp.repository.UserRepository;
import hr.pocetnik.bookingapp.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final ReviewVoteRepository reviewVoteRepository;

    public ReviewServiceImpl(
            ReviewRepository reviewRepository, ListingRepository listingRepository, UserRepository userRepository,
            ReviewVoteRepository reviewVoteRepository) {
        this.reviewRepository = reviewRepository;
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
        this.reviewVoteRepository = reviewVoteRepository;
    }

    @Override
    public List<ReviewResponse> getReviews(Long listingId) {
        return reviewRepository.findByListingIdOrderByCreatedAtDesc(listingId)
                .stream()
                .map(this::toReviewResponse)
                .toList();
    }

    @Override
    public ReviewResponse createReview(
            Long listingId,
            String userEmail,
            ReviewRequest request) {
        if (request.rating() < 1 || request.rating() > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        if (request.comment() == null || request.comment().isBlank()) {
            throw new RuntimeException("Comment is required");
        }

        ListingEntity listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ReviewEntity review = new ReviewEntity();
        review.setListing(listing);
        review.setUser(user);
        review.setRating(request.rating());
        review.setComment(request.comment().trim());

        ReviewEntity savedReview = reviewRepository.save(review);

        return toReviewResponse(savedReview);
    }

    @Override
    public ReviewResponse voteReview(
            Long reviewId,
            String userEmail,
            ReviewVoteRequest request) {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ReviewVoteEntity vote = reviewVoteRepository
                .findByReviewAndUser(review, user)
                .orElse(null);

        if (vote != null && vote.getVoteType() == request.voteType()) {
            reviewVoteRepository.delete(vote);
            return toReviewResponse(review, user);
        }

        if (vote == null) {
            vote = new ReviewVoteEntity();
            vote.setReview(review);
            vote.setUser(user);
        }

        vote.setVoteType(request.voteType());
        reviewVoteRepository.save(vote);

        return toReviewResponse(review, user);
    }

    private ReviewResponse toReviewResponse(ReviewEntity review) {
        return toReviewResponse(review, null);
    }

    private ReviewResponse toReviewResponse(ReviewEntity review, UserEntity currentUser) {
        long upvotes = reviewVoteRepository.countByReviewAndVoteType(
                review,
                ReviewVoteType.UP);

        long downvotes = reviewVoteRepository.countByReviewAndVoteType(
                review,
                ReviewVoteType.DOWN);

        ReviewVoteType currentUserVote = null;

        if (currentUser != null) {
            currentUserVote = reviewVoteRepository
                    .findByReviewAndUser(review, currentUser)
                    .map(ReviewVoteEntity::getVoteType)
                    .orElse(null);
        }

        return new ReviewResponse(
                review.getId(),
                review.getUser().getName() + " " + review.getUser().getSurname(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt(),
                upvotes,
                downvotes,
                currentUserVote);
    }

}
