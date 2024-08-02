package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class UpdateRoomDTO {

    @JsonProperty("room_type")
    @NotBlank(message = "Room type is required")
    private String roomType;

    @JsonProperty("room_price")
    @NotNull
    private Float roomPrice;

}
