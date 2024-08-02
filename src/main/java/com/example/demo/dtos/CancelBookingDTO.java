package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CancelBookingDTO {

    @JsonProperty("reason")
    @NotBlank(message = "Reason is required")
    private String reason;

}
