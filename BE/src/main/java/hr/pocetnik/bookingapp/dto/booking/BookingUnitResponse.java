package hr.pocetnik.bookingapp.dto.booking;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingUnitResponse {

    private String unitType;
    private String unitLabel;
    private Integer quantity;
}