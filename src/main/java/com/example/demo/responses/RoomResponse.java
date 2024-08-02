package com.example.demo.responses;

import com.example.demo.entities.Room;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RoomResponse {

    @JsonProperty("room_number")
    private Long roomNumber;

    @JsonProperty("room_type")
    private String roomType;

    @JsonProperty("room_price")
    private Float roomPrice;

    @JsonProperty("room_status")
    private String roomStatus;

    @JsonProperty("rating")
    private Float rating;

    @JsonProperty("rating_quantity")
    private Long ratingQuantity;

    public static RoomResponse fromRoom(Room room) {
        return RoomResponse.builder()
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .roomPrice(room.getRoomPrice())
                .roomStatus(room.getRoomStatus())
                .rating(room.getRating())
                .ratingQuantity(room.getRatingQuantity())
                .build();
    }

}
