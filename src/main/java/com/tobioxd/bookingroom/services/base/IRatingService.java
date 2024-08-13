package com.tobioxd.bookingroom.services.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import com.tobioxd.bookingroom.dtos.RatingDTO;
import com.tobioxd.bookingroom.dtos.UpdateRatingDTO;
import com.tobioxd.bookingroom.entities.Rating;
import com.tobioxd.bookingroom.responses.RatingListResponse;
import com.tobioxd.bookingroom.responses.RatingResponse;

public interface IRatingService {

    RatingResponse createRating(RatingDTO ratingDTO, String token, BindingResult result) throws Exception;

    Rating getRatingById(String ratingId) throws Exception;

    RatingResponse getRatingByIdWithToken(String ratingId, String token) throws Exception;

    RatingResponse updateRating(String ratingId, UpdateRatingDTO updateRatingDTO, String token, BindingResult result) throws Exception;

    void deleteRating(String ratingId,String token) throws Exception;

    Page<RatingResponse> ratingByUserPhoneNumber(String phoneNumber, String token, Pageable pageable) throws Exception;

    Page<RatingResponse> ratingByRoomNumber(Long roomNumber, Pageable pageable) throws Exception;

    RatingListResponse getRatingByUserPhoneNumber(String phoneNumber, String token, int page, int size) throws Exception;

    RatingListResponse getRatingByRoomNumber(Long roomNumber, int page, int size) throws Exception;

}
