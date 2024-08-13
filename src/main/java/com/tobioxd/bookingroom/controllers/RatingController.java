package com.tobioxd.bookingroom.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.tobioxd.bookingroom.dtos.RatingDTO;
import com.tobioxd.bookingroom.dtos.UpdateRatingDTO;
import com.tobioxd.bookingroom.exceptions.DataNotFoundException;
import com.tobioxd.bookingroom.exceptions.PermissionDenyException;
import com.tobioxd.bookingroom.services.impl.RatingService;

@RestController
@RequestMapping("${api.prefix}/ratings")
@RequiredArgsConstructor

public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/create")
    @Operation(summary = "Create new rating")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> createRating(@Valid @RequestBody RatingDTO ratingDTO, BindingResult result,
            @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(ratingService.createRating(ratingDTO, token, result));
        } catch (PermissionDenyException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{ratingId}")
    @Operation(summary = "Get rating by id")
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getRatingById(@PathVariable String ratingId,
            @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(ratingService.getRatingByIdWithToken(ratingId, token));
        } catch (PermissionDenyException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{ratingId}")
    @Operation(summary = "Update rating by id")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateRating(@PathVariable String ratingId,
            @Valid @RequestBody UpdateRatingDTO updateRatingDTO,
            BindingResult result, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(ratingService.updateRating(ratingId, updateRatingDTO, token, result));
        } catch (PermissionDenyException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch(DataNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{ratingId}")
    @Operation(summary = "Delete rating by id")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteRating(@PathVariable String ratingId, @RequestHeader("Authorization") String token) {
        try {
            ratingService.deleteRating(ratingId, token);
            return ResponseEntity.ok("Rating deleted successfully !");
        } catch (PermissionDenyException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch(DataNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userPhoneNumber}")
    @Operation(summary = "Get rating by user phone number")
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getRatingByUserPhoneNumber(@PathVariable String userPhoneNumber,
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            return ResponseEntity.ok(ratingService.getRatingByUserPhoneNumber(userPhoneNumber, token, page, size));
        } catch (PermissionDenyException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch(DataNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/room/{roomNumber}")
    @Operation(summary = "Get rating by room number")
    public ResponseEntity<?> getRatingByRoomNumber(@PathVariable Long roomNumber,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            return ResponseEntity.ok(ratingService.getRatingByRoomNumber(roomNumber, page, size));
        } catch(DataNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
