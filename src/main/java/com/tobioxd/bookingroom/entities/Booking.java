package com.tobioxd.bookingroom.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "booking_bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@SuppressWarnings("deprecation")
public class Booking {

    @Id
    @Column(name = "id", nullable = false)
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    private String id;
    
    @Column(name = "user_phonenumber", nullable = false)
    private String userPhoneNumber;

    @Column(name = "room_number", nullable = false)
    private Long roomNumber;

    @Column(name = "booking_date", nullable = false)
    private Date bookingDate;

    @Column(name = "checkin")
    private Date checkInDate;

    @Column(name = "checkout")
    private Date checkOutDate;

    @Column(name = "total_price")
    private Float totalPrice;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "note")
    private String note;

    @Column(name = "confirmed_by")
    private String confirmedBy;

    @Column(name = "is_rating")
    private boolean rating;

}
