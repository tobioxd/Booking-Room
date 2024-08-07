package com.tobioxd.bookingroom.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor

public class BookingListResponse {

    private List<BookingResponse> bookings;

    private int totalPages;

}
