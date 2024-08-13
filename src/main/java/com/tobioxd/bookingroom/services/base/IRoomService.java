package com.tobioxd.bookingroom.services.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;

import com.tobioxd.bookingroom.dtos.RoomDTO;
import com.tobioxd.bookingroom.dtos.UpdateRoomDTO;
import com.tobioxd.bookingroom.entities.Room;
import com.tobioxd.bookingroom.exceptions.DataNotFoundException;
import com.tobioxd.bookingroom.responses.RoomListResponse;

public interface IRoomService {

    Room createRoom(RoomDTO roomDTO, BindingResult result) throws Exception;

    Room getRoomByRoomNumber(Long roomNumber) throws Exception;
    
    Room getRoomById(String id) throws Exception;

    Room updateRoomStatus(Long roomNumber,String status) throws Exception;

    Room updateRoomInfor(Long roomNumber,UpdateRoomDTO room, BindingResult result) throws Exception;

    Page<Room> allRooms(PageRequest PageRequest) throws DataNotFoundException;

    Page<Room> availableRooms(String type, PageRequest PageRequest) throws DataNotFoundException;

    public Page<Room> availableOrCleaningRooms(PageRequest PageRequest) throws DataNotFoundException;

    RoomListResponse getAllRooms(int page, int size) throws DataNotFoundException;

    RoomListResponse getAvailableRooms(String type, int page, int size) throws Exception;

    RoomListResponse getAvailableOrCleaningRooms(int page, int size) throws Exception;

    String deleteRoom(Long roomNumber) throws Exception;

}
