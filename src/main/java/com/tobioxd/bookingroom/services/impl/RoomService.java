package com.tobioxd.bookingroom.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.tobioxd.bookingroom.dtos.RoomDTO;
import com.tobioxd.bookingroom.dtos.UpdateRoomDTO;
import com.tobioxd.bookingroom.entities.Room;
import com.tobioxd.bookingroom.exceptions.DataExistAlreadyException;
import com.tobioxd.bookingroom.exceptions.DataNotFoundException;
import com.tobioxd.bookingroom.repositories.RoomRepository;
import com.tobioxd.bookingroom.responses.RoomListResponse;
import com.tobioxd.bookingroom.responses.RoomResponse;
import com.tobioxd.bookingroom.services.base.IRoomService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;

    @Override
    public Room createRoom(RoomDTO roomDTO, BindingResult result) throws Exception {

        if (result.hasErrors()) {
            throw new Exception(result.getAllErrors().toString());
        }
        
        Long roomNumber = roomDTO.getRoomNumber();

        if(roomRepository.existsByRoomNumber(roomNumber)) {
            throw new DataExistAlreadyException("Room number exists already !");
        }

        Room newRoom = Room.builder()
                .roomNumber(roomDTO.getRoomNumber())
                .roomType(roomDTO.getRoomType())
                .roomPrice(roomDTO.getRoomPrice())
                .build();

        return roomRepository.save(newRoom);

    }

    @Override
    public Room getRoomById(String id) throws Exception {
        return roomRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Room not found !"));
    }

    @Override
    public Room updateRoomStatus(Long roomNumber, String status) throws Exception {

        Room room = getRoomByRoomNumber(roomNumber);
        room.setRoomStatus(status);
        return roomRepository.save(room);

    }

    @Override
    public Room updateRoomInfor(Long roomNumber, UpdateRoomDTO room, BindingResult result) throws Exception {

        if (result.hasErrors()) {
            throw new Exception(result.getAllErrors().toString());
        }

        Room roomToUpdate = getRoomByRoomNumber(roomNumber);
        roomToUpdate.setRoomType(room.getRoomType());
        roomToUpdate.setRoomPrice(room.getRoomPrice());

        return roomRepository.save(roomToUpdate);
    }

    @Override
    public Page<Room> allRooms(PageRequest PageRequest) throws DataNotFoundException{

        Page<Room> rooms = roomRepository.findAll(PageRequest);

        if(rooms.isEmpty()) {
            throw new DataNotFoundException("No room found !");
        }

        return rooms;

    }

    @Override
    public Page<Room> availableRooms(String type, PageRequest PageRequest) throws DataNotFoundException {
        
        Page<Room> rooms = roomRepository.findAvailableRoom(type, PageRequest);

        if(rooms.isEmpty()) {
            throw new DataNotFoundException("No room found !");
        }

        return rooms;

    }

    @Override
    public Page<Room> availableOrCleaningRooms(PageRequest PageRequest) throws DataNotFoundException {
        
        Page<Room> rooms = roomRepository.findAvailableOrCleaningRoom(PageRequest);

        if(rooms.isEmpty()) {
            throw new DataNotFoundException("No room found !");
        }

        return rooms;

    }

    @Override
    public String deleteRoom(Long roomNumber) throws Exception {
        
        Room room = getRoomByRoomNumber(roomNumber);
        room.setRoomStatus("deleted");
        roomRepository.save(room);

        return "Room deleted successfully !";
    }

    @Override
    public Room getRoomByRoomNumber(Long roomNumber) throws Exception {
        
        Room room = roomRepository.findByRoomNumber(roomNumber);

        if(room == null) {
            throw new DataNotFoundException("Room not found !");
        }

        return room;

    }

    @Override
    public RoomListResponse getAllRooms(int page, int size)  throws DataNotFoundException {

        Page<RoomResponse> rooms = allRooms(PageRequest.of(page, size, Sort.by("roomNumber")))
                    .map(RoomResponse::fromRoom);
        
        return RoomListResponse.builder()
                .rooms(rooms.getContent())
                .totalPages(rooms.getTotalPages())
                .totalPages(rooms.getTotalPages())
                .build();

    }

    @Override
    public RoomListResponse getAvailableRooms(String type, int page, int size) throws Exception {
        
        Page<RoomResponse> rooms = availableRooms(type, PageRequest.of(page, size, Sort.by("roomNumber")))
                    .map(RoomResponse::fromRoom);
        
        return RoomListResponse.builder()
                .rooms(rooms.getContent())
                .totalPages(rooms.getTotalPages())
                .totalPages(rooms.getTotalPages())
                .build();

    }

    @Override
    public RoomListResponse getAvailableOrCleaningRooms(int page, int size) throws Exception {
        
        Page<RoomResponse> rooms = availableOrCleaningRooms(PageRequest.of(page, size, Sort.by("roomNumber")))
                    .map(RoomResponse::fromRoom);
        
        return RoomListResponse.builder()
                .rooms(rooms.getContent())
                .totalPages(rooms.getTotalPages())
                .totalPages(rooms.getTotalPages())
                .build();

    }

}
