package com.tobioxd.bookingroom.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RatingResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("user_phone_number")
    private String userPhoneNumber;

    @JsonProperty("room_number")
    private Long roomNumber;

    @JsonProperty("rating")
    private int rating;

    @JsonProperty("message")
    private String message;

    @JsonProperty("created_at")
    private String createdAt;

}
