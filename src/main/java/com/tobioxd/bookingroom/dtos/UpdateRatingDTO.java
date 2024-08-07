package com.tobioxd.bookingroom.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class UpdateRatingDTO {

    @JsonProperty("rating")
    @NotNull
    private int rating;

    @JsonProperty("message")
    private String message;

}
