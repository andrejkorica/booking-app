package hr.pocetnik.bookingapp.dto.review;

import hr.pocetnik.bookingapp.enums.ReviewVoteType;

public record ReviewVoteRequest(
        ReviewVoteType voteType
) {
}