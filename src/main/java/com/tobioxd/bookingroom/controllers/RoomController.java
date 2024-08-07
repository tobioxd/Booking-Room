package com.tobioxd.bookingroom.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.tobioxd.bookingroom.dtos.RoomDTO;
import com.tobioxd.bookingroom.dtos.UpdateRoomDTO;
import com.tobioxd.bookingroom.entities.Room;
import com.tobioxd.bookingroom.responses.RoomListResponse;
import com.tobioxd.bookingroom.responses.RoomResponse;
import com.tobioxd.bookingroom.services.impl.RoomService;

@RestController
@RequestMapping("${api.prefix}/rooms")
@RequiredArgsConstructor

public class RoomController {

    private final RoomService roomService;

    @PostMapping("/create")
    @Operation(summary = "Create new room")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createRoom(@Valid @RequestBody RoomDTO roomDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        try {
            Room room = roomService.createRoom(roomDTO);
            return ResponseEntity.ok(room);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all rooms")
    public ResponseEntity<?> getAllRooms(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<RoomResponse> rooms = roomService.getAllRooms(PageRequest.of(page, size, Sort.by("roomNumber")))
                    .map(RoomResponse::fromRoom);
            return ResponseEntity.ok(RoomListResponse.builder()
                    .rooms(rooms.getContent())
                    .totalPages(rooms.getTotalPages())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{roomNumber}")
    @Operation(summary = "Get room by roomnumber")
    public ResponseEntity<?> getRoomById(@PathVariable Long roomNumber) {
        try {
            Room room = roomService.getRoomByRoomNumber(roomNumber);
            return ResponseEntity.ok(RoomResponse.fromRoom(room));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{roomNumber}")
    @Operation(summary = "Update room information")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateRoom(@PathVariable Long roomNumber, @Valid @RequestBody UpdateRoomDTO roomDTO,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        try {
            Room room = roomService.updateRoomInfor(roomNumber, roomDTO);
            return ResponseEntity.ok(RoomResponse.fromRoom(room));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{roomNumber}")
    @Operation(summary = "Delete room")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteRoom(@PathVariable Long roomNumber) {
        try {
            roomService.deleteRoom(roomNumber);
            return ResponseEntity.ok("Room deleted successfully !");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/status/{roomNumber}")
    @Operation(summary = "Update room status")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RECEPTIONIST')")
    public ResponseEntity<?> updateRoomStatus(@PathVariable Long roomNumber, @RequestParam String status) {
        try {
            Room room = roomService.updateRoomStatus(roomNumber, status);
            return ResponseEntity.ok(RoomResponse.fromRoom(room));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/available")
    @Operation(summary = "Get all available rooms")
    public ResponseEntity<?> getAvailableRooms(@RequestParam(defaultValue = "") String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {

            Page<RoomResponse> rooms = roomService
                    .getAvailableRooms(type, PageRequest.of(page, size, Sort.by("roomNumber")))
                    .map(RoomResponse::fromRoom);
            return ResponseEntity.ok(RoomListResponse.builder()
                    .rooms(rooms.getContent())
                    .totalPages(rooms.getTotalPages())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/availableOrCleaning")
    @Operation(summary = "Get all available or cleaning rooms")
    public ResponseEntity<?> getAvailableOrCleaningRooms(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<RoomResponse> rooms = roomService
                    .getAvailableOrCleaningRooms(PageRequest.of(page, size, Sort.by("roomNumber")))
                    .map(RoomResponse::fromRoom);
            return ResponseEntity.ok(RoomListResponse.builder()
                    .rooms(rooms.getContent())
                    .totalPages(rooms.getTotalPages())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
