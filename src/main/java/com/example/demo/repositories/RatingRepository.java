package com.example.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating,String> {

    @Query("SELECT o FROM Rating o WHERE o.rating >= :rating")
    Page<Rating> findByRating(@Param("rating") int rating, Pageable pageable);

    @Query("SELECT r FROM Rating r JOIN Booking b ON r.bookingId = b.id WHERE b.userPhoneNumber = :userPhoneNumber")
    Page<Rating> findByPhoneNumber(@Param("userPhoneNumber") String userPhoneNumber, Pageable pageable);

    @Query("SELECT r FROM Rating r JOIN Booking b ON r.bookingId = b.id WHERE b.roomNumber = :roomNumber")
    Page<Rating> findByRoomNumber(@Param("roomNumber") Long roomNumber, Pageable pageable);
    
} 
