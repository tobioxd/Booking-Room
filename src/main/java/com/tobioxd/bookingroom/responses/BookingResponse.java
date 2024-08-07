package com.tobioxd.bookingroom.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tobioxd.bookingroom.entities.Booking;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class BookingResponse {

    @JsonProperty("booking_id")
    private String bookingId;

    @JsonProperty("user_phonenumber")
    private String userPhoneNumber;

    @JsonProperty("room_number")
    private Long roomNumber;

    @JsonProperty("booking_date")
    private String bookingDate;

    @JsonProperty("checkin")
    private String checkInDate;

    @JsonProperty("checkout")
    private String checkOutDate;

    @JsonProperty("total_price")
    private Float totalPrice;

    @JsonProperty("status")
    private String status;

    @JsonProperty("note")
    private String note;

    @JsonProperty("confirmed_by")
    private String confirmedBy;

    @JsonProperty("is_rating")
    private Boolean isRating;

    public static BookingResponse fromBooking(Booking booking) {
        return BookingResponse.builder()
                .bookingId(booking.getId())
                .userPhoneNumber(booking.getUserPhoneNumber())
                .roomNumber(booking.getRoomNumber())
                .bookingDate(booking.getBookingDate().toString())
                .checkInDate(booking.getCheckInDate() != null ? booking.getCheckInDate().toString() : null)
                .checkOutDate(booking.getCheckOutDate() != null ? booking.getCheckOutDate().toString() : null)
                .totalPrice(booking.getTotalPrice())
                .status(booking.getStatus())
                .note(booking.getNote())
                .confirmedBy(booking.getConfirmedBy())
                .isRating(booking.isRating())
                .build();
    }

}
