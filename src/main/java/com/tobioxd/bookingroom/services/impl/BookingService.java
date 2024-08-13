package com.tobioxd.bookingroom.services.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.tobioxd.bookingroom.dtos.BookingDTO;
import com.tobioxd.bookingroom.dtos.CancelBookingDTO;
import com.tobioxd.bookingroom.entities.Booking;
import com.tobioxd.bookingroom.entities.Room;
import com.tobioxd.bookingroom.entities.User;
import com.tobioxd.bookingroom.exceptions.DataNotFoundException;
import com.tobioxd.bookingroom.exceptions.PermissionDenyException;
import com.tobioxd.bookingroom.repositories.BookingRepository;
import com.tobioxd.bookingroom.repositories.RoomRepository;
import com.tobioxd.bookingroom.responses.BookingListResponse;
import com.tobioxd.bookingroom.responses.BookingResponse;
import com.tobioxd.bookingroom.services.base.IBookingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final RoomService roomService;

    @Override
    public Booking createBooking(BookingDTO bookingDTO, String token, BindingResult result) throws Exception {

        if (result.hasErrors()) {
            throw new Exception(result.getAllErrors().toString());
        }

        Long roomNumber = bookingDTO.getRoomNumber();

        String extractedToken = token.substring(7); // Clear "Bearer" from token
        User user = userService.getUserDetailsFromToken(extractedToken);

        if (!roomRepository.existsByRoomNumber(roomNumber)) {
            throw new DataNotFoundException("Room number not found !");
        }

        if (!roomRepository.isRoomAvailable(roomNumber)) {
            throw new Exception("Room is booked !");
        }

        Booking newBooking = Booking.builder()
                .userPhoneNumber(user.getPhoneNumber())
                .roomNumber(roomNumber)
                .bookingDate(new Date())
                .totalPrice(0f)
                .build();

        roomService.updateRoomStatus(roomNumber, "booked");

        return bookingRepository.save(newBooking);

    }

    @Override
    public Booking getBookingById(String bookingId) throws Exception {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFoundException("Booking not found !"));
    }

    @Override
    public Booking getBookingByIdWithToken(String bookingId, String token) throws Exception {

        String extractedToken = token.substring(7); // Clear "Bearer" from token
        User user = userService.getUserDetailsFromToken(extractedToken);

        Booking booking = getBookingById(bookingId);

        if (!booking.getUserPhoneNumber().equals(user.getPhoneNumber())) {
            throw new Exception("You are not authorized to view this booking !");
        }

        return booking;

    }

    @Override
    public Page<Booking> allBookingWithStatus(String status, PageRequest pageRequest) throws Exception {
        Page<Booking> bookings = bookingRepository.findBookingwithStatus(status, pageRequest);

        if (bookings.isEmpty()) {
            throw new DataNotFoundException("No " + status + " booking found !");
        }

        return bookings;
    }

    @Override
    public Booking confirmBooking(String bookingId, String token) throws Exception {

        String extractedToken = token.substring(7); // Clear "Bearer" from token
        User user = userService.getUserDetailsFromToken(extractedToken);

        Booking booking = getBookingById(bookingId);

        booking.setConfirmedBy(user.getName());
        booking.setStatus("confirmed");

        return bookingRepository.save(booking);

    }

    @Override
    public Booking checkInBooking(String bookingId) throws Exception {

        Booking booking = getBookingById(bookingId);

        booking.setCheckInDate(new Date());
        booking.setStatus("operational");

        return bookingRepository.save(booking);

    }

    @Override
    public Booking checkOutBooking(String bookingId) throws Exception {

        Booking booking = getBookingById(bookingId);

        booking.setCheckOutDate(new Date());
        booking.setStatus("completed");

        Room room = roomRepository.findByRoomNumber(booking.getRoomNumber());
        roomService.updateRoomStatus(room.getRoomNumber(), "cleaning");

        Instant checkInInstant = booking.getCheckInDate().toInstant();
        Instant checkOutInstant = booking.getCheckOutDate().toInstant();

        Duration duration = Duration.between(checkInInstant, checkOutInstant);
        float daysBetween = duration.toMillis() / (1000f * 60 * 60 * 24);

        float totalPrice = daysBetween * room.getRoomPrice();

        booking.setTotalPrice(totalPrice);

        return bookingRepository.save(booking);

    }

    @Override
    public Booking cancelBooking(String bookingId, CancelBookingDTO cancelBookingDTO, String token, BindingResult result) throws Exception {

        if (result.hasErrors()) {
            throw new Exception(result.getAllErrors().toString());
        }
 
        String extractedToken = token.substring(7); // Clear "Bearer" from token
        User user = userService.getUserDetailsFromToken(extractedToken);

        Booking booking = getBookingById(bookingId);

        if (!booking.getUserPhoneNumber().equals(user.getPhoneNumber())) {
            throw new PermissionDenyException("You are not authorized to cancel this booking !");
        }

        booking.setNote(cancelBookingDTO.getReason());
        booking.setStatus("cancelled");

        roomService.updateRoomStatus(booking.getRoomNumber(), "available");

        return bookingRepository.save(booking);

    }

    @Override
    public Page<Booking> bookingByUserPhoneNumber(String phoneNumber, PageRequest pageRequest, String token)
            throws Exception {

        String extractedToken = token.substring(7); // Clear "Bearer" from token
        User user = userService.getUserDetailsFromToken(extractedToken);

        if (user.getRole().equals("user")) {
            if (!user.getPhoneNumber().equals(phoneNumber)) {
                throw new PermissionDenyException("You are not authorized to view this booking!");
            }
        }

        Page<Booking> bookings = bookingRepository.findByUserPhoneNumber(phoneNumber, pageRequest);

        if (bookings.isEmpty()) {
            throw new DataNotFoundException("No booking found !");
        }

        return bookings;

    }

    @Override
    public Page<Booking> bookingByRoomNumber(Long roomNumber, PageRequest pageRequest) throws Exception {

        Page<Booking> bookings = bookingRepository.findByRoomNumber(roomNumber, pageRequest);

        if (bookings.isEmpty()) {
            throw new DataNotFoundException("No booking found !");
        }

        return bookings;

    }

    @Override
    public Page<Booking> bookingByPrice(Float price, PageRequest pageRequest) throws Exception {

        Page<Booking> bookings = bookingRepository.findBookingByPrice(price, pageRequest);

        if (bookings.isEmpty()) {
            throw new DataNotFoundException("No booking found !");
        }

        return bookings;

    }

    @Override
    public Page<Booking> bookingByDate(String date, PageRequest pageRequest) throws Exception {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date bookingDate = dateFormat.parse(date);

        System.out.println(bookingDate);

        Page<Booking> bookings = bookingRepository.findByBookingDate(bookingDate, pageRequest);

        if (bookings.isEmpty()) {
            throw new DataNotFoundException("No booking found !");
        }

        return bookings;

    }

    @Override
    public Float getBookingCountAndTotalPrice() throws Exception {
        return bookingRepository.findTotalPrice();
    }

    @Override
    public BookingListResponse getBookingByUserPhoneNumber(String userPhoneNumber, int page, int size, String token)
            throws Exception {

        Page<BookingResponse> bookings = bookingByUserPhoneNumber(userPhoneNumber,
                PageRequest.of(page, size, Sort.by("bookingDate").descending()), token)
                .map(BookingResponse::fromBooking);

        return BookingListResponse.builder()
                .bookings(bookings.getContent())
                .totalPages(bookings.getTotalPages())
                .build();

    }

    @Override
    public BookingListResponse getBookingByRoomNumber(Long roomNumber, int page, int size) throws Exception {

        Page<BookingResponse> bookings = bookingByRoomNumber(roomNumber,
                PageRequest.of(page, size, Sort.by("bookingDate").descending()))
                .map(BookingResponse::fromBooking);

        return BookingListResponse.builder()
                .bookings(bookings.getContent())
                .totalPages(bookings.getTotalPages())
                .build();

    }

    @Override
    public BookingListResponse getAllBookingWithStatus(String status, int page, int size) throws Exception {

        Page<BookingResponse> bookings = allBookingWithStatus(status,
                PageRequest.of(page, size, Sort.by("bookingDate").descending()))
                .map(BookingResponse::fromBooking);

        return BookingListResponse.builder()
                .bookings(bookings.getContent())
                .totalPages(bookings.getTotalPages())
                .build();

    }

    @Override
    public BookingListResponse getBookingByPrice(Float price, int page, int size) throws Exception {

        Page<BookingResponse> bookings = bookingByPrice(price,
                PageRequest.of(page, size, Sort.by("bookingDate").descending()))
                .map(BookingResponse::fromBooking);

        return BookingListResponse.builder()
                .bookings(bookings.getContent())
                .totalPages(bookings.getTotalPages())
                .build();

    }

    @Override
    public BookingListResponse getBookingByDate(String date, int page, int size) throws Exception {

        Page<BookingResponse> bookings = bookingByDate(date,
                PageRequest.of(page, size, Sort.by("bookingDate").descending()))
                .map(BookingResponse::fromBooking);

        return BookingListResponse.builder()
                .bookings(bookings.getContent())
                .totalPages(bookings.getTotalPages())
                .build();

    }

}
