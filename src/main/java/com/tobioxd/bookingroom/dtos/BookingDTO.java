package com.tobioxd.bookingroom.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class BookingDTO {

    @JsonProperty("room_number")
    @NotNull
    private Long roomNumber;

}
