package com.example.demo.services.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.demo.dtos.RoomDTO;
import com.example.demo.dtos.UpdateRoomDTO;
import com.example.demo.entities.Room;

public interface IRoomService {

    Room createRoom(RoomDTO roomDTO) throws Exception;

    Room getRoomByRoomNumber(Long roomNumber) throws Exception;
    
    Room getRoomById(String id) throws Exception;

    Room updateRoomStatus(Long roomNumber,String status) throws Exception;

    Room updateRoomInfor(Long roomNumber,UpdateRoomDTO room) throws Exception;

    Page<Room> getAllRooms(PageRequest PageRequest) throws Exception;

    Page<Room> getAvailableRooms(String type, PageRequest PageRequest) throws Exception;

    Page<Room> getAvailableOrCleaningRooms(PageRequest PageRequest) throws Exception;

    void deleteRoom(Long roomNumber) throws Exception;

}
