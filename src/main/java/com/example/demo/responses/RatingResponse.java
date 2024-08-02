package com.example.demo.responses;

import com.example.demo.entities.Rating;
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

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("room_number")
    private Long roomNumber;

    @JsonProperty("rating")
    private int rating;

    @JsonProperty("message")
    private String message;

    @JsonProperty("created_at")
    private String createdAt;

    public static RatingResponse fromRating(Rating rating) {
        return RatingResponse.builder()
                .id(rating.getId())
                .userName(rating.getUserName())
                .roomNumber(rating.getRoomNumber())
                .rating(rating.getRating())
                .message(rating.getMessage())
                .createdAt(rating.getCreatedAt().toString())
                .build();
    }
    

}
