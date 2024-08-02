package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class RoomDTO {

    @JsonProperty("room_number")
    @NotBlank(message = "Room number is required")
    private Long roomNumber;

    @JsonProperty("room_type")
    @NotBlank(message = "Room type is required")
    private String roomType;

    @JsonProperty("room_price")
    @NotNull
    private Float roomPrice;

}
