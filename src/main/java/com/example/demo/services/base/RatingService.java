package com.example.demo.services.base;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.RatingDTO;
import com.example.demo.dtos.UpdateRatingDTO;
import com.example.demo.entities.Booking;
import com.example.demo.entities.Rating;
import com.example.demo.entities.Room;
import com.example.demo.entities.User;
import com.example.demo.exceptions.DataNotFoundException;
import com.example.demo.repositories.BookingRepository;
import com.example.demo.repositories.RatingRepository;
import com.example.demo.repositories.RoomRepository;
import com.example.demo.services.impl.IRatingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingService implements IRatingService {

    private final RatingRepository ratingRepository;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserService userService;

    @Override
    public Rating createRating(RatingDTO ratingDTO, String token) throws Exception {

        String bookingId = ratingDTO.getBookingId();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking not found !"));

        String extractedToken = token.substring(7); // Clear "Bearer" from token
        User user = userService.getUserDetailsFromToken(extractedToken);

        if (!booking.getUserPhoneNumber().equals(user.getPhoneNumber())) {
            throw new Exception("You are not allowed to rate this booking !");
        }

        if (booking.getCheckOutDate() == null) {
            throw new Exception("You must check-out before rating !");
        }

        if (booking.isRating()) {
            throw new Exception("You have already rated this booking !");
        }

        if (ratingDTO.getRating() > 5) {
            throw new Exception("Rating must be less than or equal to 5 !");
        }

        Rating newRating = Rating.builder()
                .bookingId(bookingId)
                .userPhoneNumber(user.getPhoneNumber())
                .userName(user.getName())
                .roomNumber(booking.getRoomNumber())
                .rating(ratingDTO.getRating())
                .message(ratingDTO.getMessage())
                .createdAt(new Date())
                .build();

        booking.setRating(true);

        Room room = roomRepository.findByRoomNumber(booking.getRoomNumber());

        if (room.getRating() != 0 && room.getRatingQuantity() != 0) {

            float sum = room.getRating() * room.getRatingQuantity();
            Long newquantity = room.getRatingQuantity() + 1;
            float newrating = (sum + newRating.getRating()) / (newquantity);
            room.setRating(newrating);
            room.setRatingQuantity(newquantity);

        } else {

            room.setRating((float) newRating.getRating());
            room.setRatingQuantity((long) 1);
        }

        return ratingRepository.save(newRating);

    }

    @Override
    public Rating getRatingById(String ratingId) throws Exception {
        return ratingRepository.findById(ratingId)
                .orElseThrow(() -> new DataNotFoundException("Rating not found !"));
    }

    @Override
    public Rating getRatingByIdWithToken(String ratingId, String token) throws Exception {

        String extractedToken = token.substring(7); // Clear "Bearer" from token
        User user = userService.getUserDetailsFromToken(extractedToken);

        Rating rating = getRatingById(ratingId);

        Booking booking = bookingRepository.findById(rating.getBookingId())
                .orElseThrow(() -> new Exception("Booking not found !"));

        if (user.getRole().equals("user")) {
            if (!booking.getUserPhoneNumber().equals(user.getPhoneNumber())) {
                throw new Exception("You are not allowed to view this rating !");
            }
        }

        return rating;

    }

    @Override
    public Rating updateRating(String ratingId, UpdateRatingDTO updateRatingDTO, String token) throws Exception {

        String extractedToken = token.substring(7); // Clear "Bearer" from token
        User user = userService.getUserDetailsFromToken(extractedToken);

        Rating rating = getRatingById(ratingId);

        Booking booking = bookingRepository.findById(rating.getBookingId())
                .orElseThrow(() -> new Exception("Booking not found !"));

        if (!booking.getUserPhoneNumber().equals(user.getPhoneNumber())) {
            throw new Exception("You are not allowed to rate this booking !");
        }

        rating.setRating(updateRatingDTO.getRating());
        rating.setMessage(updateRatingDTO.getMessage());

        Room room = roomRepository.findByRoomNumber(rating.getRoomNumber());

        float sum = room.getRating() * room.getRatingQuantity();
        float newrating = (sum + updateRatingDTO.getRating() - room.getRating()) / (room.getRatingQuantity());
        room.setRating(newrating);

        return ratingRepository.save(rating);

    }

    @Override
    public void deleteRating(String ratingId, String token) throws Exception {

        String extractedToken = token.substring(7); // Clear "Bearer" from token
        User user = userService.getUserDetailsFromToken(extractedToken);

        Rating rating = getRatingById(ratingId);

        Booking booking = bookingRepository.findById(rating.getBookingId())
                .orElseThrow(() -> new Exception("Booking not found !"));

        if (!booking.getUserPhoneNumber().equals(user.getPhoneNumber())) {
            throw new Exception("You are not allowed to delete this rating !");
        }

        Room room = roomRepository.findByRoomNumber(rating.getRoomNumber());

        if (room.getRatingQuantity() == 1) {

            room.setRating((float) 0);
            room.setRatingQuantity((long) 0);

        } else {

            float sum = room.getRating() * room.getRatingQuantity();
            Long newquantity = room.getRatingQuantity() - 1;
            float newrating = (sum - rating.getRating()) / (newquantity);
            room.setRating(newrating);
            room.setRatingQuantity(newquantity);

        }

        booking.setRating(false);

        ratingRepository.delete(rating);

    }

    @Override
    public Page<Rating> getRatingByUserPhoneNumber(String phoneNumber, String token, Pageable pageable)
            throws Exception {

        String extractedToken = token.substring(7); // Clear "Bearer" from token
        User user = userService.getUserDetailsFromToken(extractedToken);

        System.out.println(user.getRole());
        System.out.println(phoneNumber);
        System.out.println(user.getPhoneNumber());

        if (user.getRole().equals("user")) {
            if (!phoneNumber.equals(user.getPhoneNumber())) {
                throw new Exception("You are not allowed to view these rating !");
            }
        }

        Page<Rating> ratings = ratingRepository.findByPhoneNumber(phoneNumber, pageable);

        if (ratings.isEmpty()) {
            throw new DataNotFoundException("No booking found !");
        }

        return ratings;

    }

    @Override
    public Page<Rating> getRatingByRoomNumber(Long roomNumber, Pageable pageable) throws Exception {

        Page<Rating> ratings = ratingRepository.findByRoomNumber(roomNumber, pageable);

        if (ratings.isEmpty()) {
            throw new DataNotFoundException("No booking found !");
        }

        return ratings;

    }

}
