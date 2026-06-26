package hr.pocetnik.bookingapp.dto.listing;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListingAvailableUnitResponse {
    private String type;
    private String label;
    private Integer quantity;
    private Integer availableQuantity;
    private Integer maxGuests;
    private BigDecimal pricePerNight;
}