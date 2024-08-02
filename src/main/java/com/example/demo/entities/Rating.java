package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ratings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@SuppressWarnings("deprecation")
public class Rating {

    @Id
    @Column(name = "id", nullable = false)
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    private String id;

    @Column(name = "booking_id", nullable = false)
    private String bookingId;

    @Column(name = "user_phonenumber", nullable = false)
    private String userPhoneNumber;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "room_number", nullable = false)
    private Long roomNumber;

    @Column(name = "rating")
    private int rating;

    @Column(name = "message")
    private String message;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

}