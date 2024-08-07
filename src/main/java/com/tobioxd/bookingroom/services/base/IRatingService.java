package com.tobioxd.bookingroom.services.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tobioxd.bookingroom.dtos.RatingDTO;
import com.tobioxd.bookingroom.dtos.UpdateRatingDTO;
import com.tobioxd.bookingroom.entities.Rating;
import com.tobioxd.bookingroom.responses.RatingResponse;

public interface IRatingService {

    RatingResponse createRating(RatingDTO ratingDTO, String token) throws Exception;

    Rating getRatingById(String ratingId) throws Exception;

    RatingResponse getRatingByIdWithToken(String ratingId, String token) throws Exception;

    RatingResponse updateRating(String ratingId, UpdateRatingDTO updateRatingDTO, String token) throws Exception;

    void deleteRating(String ratingId,String token) throws Exception;

    Page<RatingResponse> getRatingByUserPhoneNumber(String phoneNumber, String token, Pageable pageable) throws Exception;

    Page<RatingResponse> getRatingByRoomNumber(Long roomNumber, Pageable pageable) throws Exception;

}
