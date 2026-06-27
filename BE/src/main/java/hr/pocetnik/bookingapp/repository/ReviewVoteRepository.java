package hr.pocetnik.bookingapp.repository;

import hr.pocetnik.bookingapp.enums.ReviewVoteType;
import hr.pocetnik.bookingapp.model.ReviewEntity;
import hr.pocetnik.bookingapp.model.ReviewVoteEntity;
import hr.pocetnik.bookingapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface ReviewVoteRepository
        extends JpaRepository<ReviewVoteEntity, Long> {

    Optional<ReviewVoteEntity> findByReviewAndUser(
            ReviewEntity review,
            UserEntity user);

    long countByReviewAndVoteType(
            ReviewEntity review,
            ReviewVoteType voteType);
}