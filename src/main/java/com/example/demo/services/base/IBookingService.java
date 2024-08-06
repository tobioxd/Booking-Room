package com.example.demo.services.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.demo.dtos.BookingDTO;
import com.example.demo.dtos.CancelBookingDTO;
import com.example.demo.entities.Booking;


public interface IBookingService {

    Booking createBooking(BookingDTO bookingDTO, String token) throws Exception;

    Booking getBookingById(String bookingId) throws Exception;

    Booking getBookingByIdWithToken(String bookingId, String token) throws Exception;

    Page<Booking> getAllBookingWithStatus(String status, PageRequest pageRequest) throws Exception;

    Booking confirmBooking(String bookingId, String token) throws Exception;

    Booking checkInBooking(String bookingId) throws Exception;

    Booking checkOutBooking(String bookingId) throws Exception;

    Booking cancelBooking(String bookingId, CancelBookingDTO cancelBookingDTO, String token) throws Exception;

    Page<Booking> getBookingByUserPhoneNumber(String phoneNumber, PageRequest pageRequest, String token) throws Exception;

    Page<Booking> getBookingByRoomNumber(Long roomNumber, PageRequest pageRequest) throws Exception;

    Page<Booking> getBookingByPrice(Float price, PageRequest pageRequest) throws Exception;

    Page<Booking> getBookingByDate(String date, PageRequest pageRequest) throws Exception;

    Float getBookingCountAndTotalPrice() throws Exception;

}
