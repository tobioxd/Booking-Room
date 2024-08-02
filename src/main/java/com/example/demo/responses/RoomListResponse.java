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

public class RoomListResponse {

    private List<RoomResponse> rooms;

    private int totalPages;

}
