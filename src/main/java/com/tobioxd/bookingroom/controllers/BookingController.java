package com.tobioxd.bookingroom.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.tobioxd.bookingroom.dtos.BookingDTO;
import com.tobioxd.bookingroom.dtos.CancelBookingDTO;
import com.tobioxd.bookingroom.exceptions.DataNotFoundException;
import com.tobioxd.bookingroom.exceptions.PermissionDenyException;
import com.tobioxd.bookingroom.responses.BookingResponse;
import com.tobioxd.bookingroom.services.impl.BookingService;

@RestController
@RequestMapping("${api.prefix}/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/create")
    @Operation(summary = "Create new booking")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingDTO bookingDTO, BindingResult result,
            @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(BookingResponse.fromBooking(bookingService.createBooking(bookingDTO, token, result)));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all bookings")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllBookings(@RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            return ResponseEntity.ok().body(bookingService.getAllBookingWithStatus(status, page, size));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{bookingId}")
    @Operation(summary = "Get booking by id")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER') OR hasRole('ROLE_RECEPTIONIST')")
    public ResponseEntity<?> getBookingById(@PathVariable String bookingId,
            @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity
                    .ok(BookingResponse.fromBooking(bookingService.getBookingByIdWithToken(bookingId, token)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/confirm/{bookingId}")
    @Operation(summary = "Confirm booking")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> confirmBooking(@PathVariable String bookingId,
            @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(BookingResponse.fromBooking(bookingService.confirmBooking(bookingId, token)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/cancel/{bookingId}")
    @Operation(summary = "Cancel booking")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> cancelBooking(@PathVariable String bookingId,
            @Valid @RequestBody CancelBookingDTO cancelBookingDTO, BindingResult result,
            @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(BookingResponse.fromBooking(bookingService.cancelBooking(bookingId, cancelBookingDTO, token, result)));
        } catch (PermissionDenyException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/checkin/{bookingId}")
    @Operation(summary = "Check in booking")
    @PreAuthorize("hasRole('ROLE_RECEPTIONIST')")
    public ResponseEntity<?> checkInBooking(@PathVariable String bookingId) {
        try {
            return ResponseEntity.ok(BookingResponse.fromBooking(bookingService.checkInBooking(bookingId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/checkout/{bookingId}")
    @Operation(summary = "Check out booking")
    @PreAuthorize("hasRole('ROLE_RECEPTIONIST')")
    public ResponseEntity<?> checkOutBooking(@PathVariable String bookingId) {
        try {
            return ResponseEntity.ok(BookingResponse.fromBooking(bookingService.checkOutBooking(bookingId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{phoneNumber}")
    @Operation(summary = "Get booking by user phone number")
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getBookingByUserPhoneNumber(@RequestHeader("Authorization") String token,
            @PathVariable String phoneNumber,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            return ResponseEntity.ok().body(bookingService.getBookingByUserPhoneNumber(phoneNumber, page, size, token));
        } catch (PermissionDenyException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/room/{roomNumber}")
    @Operation(summary = "Get booking by room number")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getBookingByRoomNumber(@PathVariable Long roomNumber,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            return ResponseEntity.ok().body(bookingService.getBookingByRoomNumber(roomNumber, page, size));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/price/{price}")
    @Operation(summary = "Get booking equal to price")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getBookingByPrice(@PathVariable Float price, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            return ResponseEntity.ok().body(bookingService.getBookingByPrice(price, page, size));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/date/{date}")
    @Operation(summary = "Get booking by date")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getBookingByDate(@PathVariable String date, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            return ResponseEntity.ok().body(bookingService.getBookingByDate(date, page, size));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/count")
    @Operation(summary = "Get total price")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getBookingCountAndTotalPrice() {
        try {
            return ResponseEntity.ok(bookingService.getBookingCountAndTotalPrice());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
