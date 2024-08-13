package com.tobioxd.bookingroom.entities;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "booking_rooms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@SuppressWarnings("deprecation")
public class Room {

    @Id
    @Column(name = "id", nullable = false)
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    private String id;

    @Column(name = "room_number", nullable = false)
    private Long roomNumber;

    @Column(name = "room_type", length = 255, nullable = false)
    private String roomType;

    @Column(name = "room_price", nullable = false)
    private Float roomPrice;

    @Column(name = "room_status", length = 255, nullable = false)
    private String roomStatus;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "rating_quantity")
    private Long ratingQuantity;

}
