package com.example.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

import com.example.demo.entities.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    
    @Query("SELECT o FROM Booking o WHERE o.userPhoneNumber = :phoneNumber")
    Page<Booking> findByUserPhoneNumber(String phoneNumber, Pageable pageable);

    @Query("SELECT o FROM Booking o WHERE o.roomNumber = :roomNumber")
    Page<Booking> findByRoomNumber(Long roomNumber, Pageable pageable);

    @Query("SELECT o FROM Booking o WHERE o.bookingDate >= :bookingDate")
    Page<Booking> findByBookingDate(Date bookingDate, Pageable pageable);

    @Query("SELECT o FROM Booking o WHERE " +
           "(:status IS NULL OR :status = '') OR o.status = :status")
    Page<Booking> findBookingwithStatus(String status, Pageable pageable);

    @Query("SELECT o FROM Booking o WHERE o.totalPrice >= :price")
    Page<Booking> findBookingByPrice(Float price, Pageable pageable);
    
    @Query("SELECT SUM(o.totalPrice) FROM Booking o")
    Float findTotalPrice();

} 
