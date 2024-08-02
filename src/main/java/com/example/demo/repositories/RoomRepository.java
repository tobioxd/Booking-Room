package com.example.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Room o WHERE (o.roomNumber = :roomNumber AND o.roomStatus != 'deleted')")
    boolean existsByRoomNumber(Long roomNumber);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Room o WHERE (o.roomNumber = :roomNumber AND (o.roomStatus = 'available' OR o.roomStatus = 'cleaning'))")
    boolean isRoomAvailable(Long roomNumber);

    Room findByRoomNumber(Long roomNumber);

    @Query("SELECT o FROM Room o WHERE o.roomStatus != 'deleted'")
    Page<Room> findAllRoom(Pageable pageable);

    @Query("SELECT o FROM Room o WHERE o.roomStatus = 'available'")
    Page<Room> findAvailableRoom(Pageable pageable);

    @Query("SELECT o FROM Room o WHERE (o.roomStatus = 'available' OR o.roomStatus = 'cleaning')")
    Page<Room> findAvailableOrCleaningRoom(Pageable pageable);

    @Query("SELECT o FROM Room o WHERE o.roomStatus = 'available' AND o.roomType = 'single'")
    Page<Room> findAvailableSingleRoom(Pageable pageable);

    @Query("SELECT o FROM Room o WHERE o.roomStatus = 'available' AND o.roomType = 'couple'")
    Page<Room> findAvailableCoupleRoom(Pageable pageable);

}