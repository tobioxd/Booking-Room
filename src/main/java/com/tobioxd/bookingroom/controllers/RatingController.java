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

import com.tobioxd.bookingroom.dtos.RatingDTO;
import com.tobioxd.bookingroom.dtos.UpdateRatingDTO;
import com.tobioxd.bookingroom.responses.RatingListResponse;
import com.tobioxd.bookingroom.responses.RatingResponse;
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
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        try {
            RatingResponse rating = ratingService.createRating(ratingDTO, token);
            return ResponseEntity.ok(rating);
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
            RatingResponse rating = ratingService.getRatingByIdWithToken(ratingId, token);
            return ResponseEntity.ok(rating);
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
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        try {
            RatingResponse rating = ratingService.updateRating(ratingId, updateRatingDTO, token);
            return ResponseEntity.ok(rating);
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
            Page<RatingResponse> ratings = ratingService
                    .getRatingByUserPhoneNumber(userPhoneNumber, token,
                            PageRequest.of(page, size, Sort.by("createdAt").descending()));
            return ResponseEntity.ok(RatingListResponse.builder()
                    .ratings(ratings.getContent())
                    .totalPages(ratings.getTotalPages())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/room/{roomNumber}")
    @Operation(summary = "Get rating by room number")
    public ResponseEntity<?> getRatingByRoomNumber(@PathVariable Long roomNumber,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            Page<RatingResponse> ratings = ratingService
                    .getRatingByRoomNumber(roomNumber, PageRequest.of(page, size, Sort.by("createdAt").descending()));
            return ResponseEntity.ok(RatingListResponse.builder()
                    .ratings(ratings.getContent())
                    .totalPages(ratings.getTotalPages())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
