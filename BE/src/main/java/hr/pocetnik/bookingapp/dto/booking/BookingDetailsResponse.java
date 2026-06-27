package hr.pocetnik.bookingapp.dto.booking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import hr.pocetnik.bookingapp.enums.BookingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingDetailsResponse {

    private Long id;

    private Long listingId;
    private String listingTitle;
    private String listingLocation;
    private String listingImage;

    private List<BookingUnitResponse> units;

    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer nights;

    private BigDecimal pricePerNight;
    private BigDecimal totalPrice;

    private String guestName;
    private String guestSurname;
    private String guestEmail;
    private String guestPhoneNumber;

    private String travelPurpose;
    private String arrivalTime;
    private String arrivalMethod;
    private String specialRequests;
    private Boolean hasPets;
    private Boolean needsParking;
    private String accessibilityRequirements;

    private String billingAddress;

    private Boolean agreedToRules;
    private Boolean agreedToCancellationPolicy;
    private Boolean confirmedInfoCorrect;

    private BookingStatus status;

    private LocalDateTime createdAt;
}