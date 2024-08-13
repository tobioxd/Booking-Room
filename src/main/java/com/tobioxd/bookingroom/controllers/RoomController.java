package com.tobioxd.bookingroom.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.tobioxd.bookingroom.dtos.RoomDTO;
import com.tobioxd.bookingroom.dtos.UpdateRoomDTO;
import com.tobioxd.bookingroom.exceptions.DataExistAlreadyException;
import com.tobioxd.bookingroom.exceptions.DataNotFoundException;
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
        try {
            return ResponseEntity.ok(roomService.createRoom(roomDTO, result));
        }catch (DataExistAlreadyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all rooms")
    public ResponseEntity<?> getAllRooms(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(roomService.getAllRooms(page, size));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{roomNumber}")
    @Operation(summary = "Get room by roomnumber")
    public ResponseEntity<?> getRoomById(@PathVariable Long roomNumber) {
        try {
            return ResponseEntity.ok(RoomResponse.fromRoom(roomService.getRoomByRoomNumber(roomNumber)));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{roomNumber}")
    @Operation(summary = "Update room information")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateRoom(@PathVariable Long roomNumber, @Valid @RequestBody UpdateRoomDTO roomDTO,
            BindingResult result) {
        try {
            return ResponseEntity.ok(roomService.updateRoomInfor(roomNumber, roomDTO,result));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{roomNumber}")
    @Operation(summary = "Delete room")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteRoom(@PathVariable Long roomNumber) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(roomService.deleteRoom(roomNumber));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/status/{roomNumber}")
    @Operation(summary = "Update room status")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RECEPTIONIST')")
    public ResponseEntity<?> updateRoomStatus(@PathVariable Long roomNumber, @RequestParam String status) {
        try {
            return ResponseEntity.ok(RoomResponse.fromRoom(roomService.updateRoomStatus(roomNumber, status)));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/available")
    @Operation(summary = "Get all available rooms")
    public ResponseEntity<?> getAvailableRooms(@RequestParam(defaultValue = "") String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(roomService.getAvailableRooms(type, page, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/availableOrCleaning")
    @Operation(summary = "Get all available or cleaning rooms")
    public ResponseEntity<?> getAvailableOrCleaningRooms(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(roomService.getAvailableOrCleaningRooms(page, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
