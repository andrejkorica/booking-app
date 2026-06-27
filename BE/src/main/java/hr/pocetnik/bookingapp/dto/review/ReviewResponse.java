package hr.pocetnik.bookingapp.dto.review;

import java.time.LocalDateTime;

import hr.pocetnik.bookingapp.enums.ReviewVoteType;

public record ReviewResponse(
        Long id,
        String userName,
        int rating,
        String comment,
        LocalDateTime createdAt,
        long upvotes,
        long downvotes,
        ReviewVoteType currentUserVote
) {
}