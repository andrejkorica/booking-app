package hr.pocetnik.bookingapp.model;

import hr.pocetnik.bookingapp.enums.ReviewVoteType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"review_id", "user_id"})
    }
)
public class ReviewVoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReviewVoteType voteType;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private ReviewEntity review;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}