package com.tobioxd.bookingroom.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class RatingDTO {

    @JsonProperty("booking_id")
    @NotBlank(message = "Booking id is mandatory")
    private String bookingId;

    @JsonProperty("rating")
    @NotNull
    private int rating;

    @JsonProperty("message")
    private String message;

}
