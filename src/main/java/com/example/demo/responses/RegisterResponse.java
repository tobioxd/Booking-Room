package com.example.demo.responses;

import com.example.demo.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RegisterResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("user")
    private User user;
    
}