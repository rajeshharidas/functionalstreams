package com.bookedevents.streams.demo;


import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@ToString
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Booking implements Serializable {

    private UUID bookingUuid;

    @NotBlank
    private String facilityName;

    private BookingStatus bookingStatus;
}
