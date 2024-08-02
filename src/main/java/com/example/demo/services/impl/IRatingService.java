package com.example.demo.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dtos.RatingDTO;
import com.example.demo.dtos.UpdateRatingDTO;
import com.example.demo.entities.Rating;

public interface IRatingService {

    Rating createRating(RatingDTO ratingDTO, String token) throws Exception;

    Rating getRatingById(String ratingId) throws Exception;

    Rating getRatingByIdWithToken(String ratingId, String token) throws Exception;

    Rating updateRating(String ratingId, UpdateRatingDTO updateRatingDTO, String token) throws Exception;

    void deleteRating(String ratingId,String token) throws Exception;

    Page<Rating> getRatingByUserPhoneNumber(String phoneNumber, String token, Pageable pageable) throws Exception;

    Page<Rating> getRatingByRoomNumber(Long roomNumber, Pageable pageable) throws Exception;

}
