package com.tobioxd.bookingroom.services.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;

import com.tobioxd.bookingroom.dtos.BookingDTO;
import com.tobioxd.bookingroom.dtos.CancelBookingDTO;
import com.tobioxd.bookingroom.entities.Booking;
import com.tobioxd.bookingroom.responses.BookingListResponse;


public interface IBookingService {

    Booking createBooking(BookingDTO bookingDTO, String token, BindingResult result) throws Exception;

    Booking getBookingById(String bookingId) throws Exception;

    Booking getBookingByIdWithToken(String bookingId, String token) throws Exception;

    Booking confirmBooking(String bookingId, String token) throws Exception;

    Booking checkInBooking(String bookingId) throws Exception;

    Booking checkOutBooking(String bookingId) throws Exception;

    Booking cancelBooking(String bookingId, CancelBookingDTO cancelBookingDTO, String token, BindingResult result) throws Exception;

    Page<Booking> bookingByUserPhoneNumber(String phoneNumber, PageRequest pageRequest, String token) throws Exception;

    Page<Booking> bookingByRoomNumber(Long roomNumber, PageRequest pageRequest) throws Exception;

    Page<Booking> allBookingWithStatus(String status, PageRequest pageRequest) throws Exception;

    Page<Booking> bookingByPrice(Float price, PageRequest pageRequest) throws Exception;

    Page<Booking> bookingByDate(String date, PageRequest pageRequest) throws Exception;

    Float getBookingCountAndTotalPrice() throws Exception;

    BookingListResponse getBookingByUserPhoneNumber(String userPhoneNumber,int page, int size, String token) throws Exception;

    BookingListResponse getBookingByRoomNumber(Long roomNumber,int page, int size) throws Exception;

    BookingListResponse getAllBookingWithStatus(String status,int page, int size) throws Exception;

    BookingListResponse getBookingByPrice(Float price,int page, int size) throws Exception;

    BookingListResponse getBookingByDate(String date,int page, int size) throws Exception;

}
