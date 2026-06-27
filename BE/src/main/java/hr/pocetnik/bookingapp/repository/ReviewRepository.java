package hr.pocetnik.bookingapp.repository;

import hr.pocetnik.bookingapp.dto.review.ReviewResponse;
import hr.pocetnik.bookingapp.dto.review.ReviewVoteRequest;
import hr.pocetnik.bookingapp.enums.ReviewVoteType;
import hr.pocetnik.bookingapp.model.ReviewEntity;
import hr.pocetnik.bookingapp.model.ReviewVoteEntity;
import hr.pocetnik.bookingapp.model.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository
        extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByListingIdOrderByCreatedAtDesc(Long listingId);

}