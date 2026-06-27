package hr.pocetnik.bookingapp.dto.review;

public record ReviewRequest(
        int rating,
        String comment
) {
}