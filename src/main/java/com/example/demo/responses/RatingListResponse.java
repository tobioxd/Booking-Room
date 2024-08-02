package com.example.demo.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor

public class RatingListResponse {

    private List<RatingResponse> ratings;

    private int totalPages;


}
