package com.bookedevents.streams.demo;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("bookFacility")
    public Booking placeBooking(@RequestBody @NotNull(message = "Invalid Booking") Booking booking) {
        return bookingService.placeBooking().apply(booking);
    }

}
