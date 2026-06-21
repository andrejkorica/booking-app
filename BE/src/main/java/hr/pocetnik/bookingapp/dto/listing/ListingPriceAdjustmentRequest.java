package hr.pocetnik.bookingapp.dto.listing;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ListingPriceAdjustmentRequest {

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal percent;
}