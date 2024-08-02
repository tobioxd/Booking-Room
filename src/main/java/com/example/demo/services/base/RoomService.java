package com.example.demo.services.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.RoomDTO;
import com.example.demo.dtos.UpdateRoomDTO;
import com.example.demo.entities.Room;
import com.example.demo.exceptions.DataNotFoundException;
import com.example.demo.repositories.RoomRepository;
import com.example.demo.services.impl.IRoomService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;

    @Override
    public Room createRoom(RoomDTO roomDTO) throws Exception {
        
        Long roomNumber = roomDTO.getRoomNumber();

        if(roomRepository.existsByRoomNumber(roomNumber)) {
            throw new Exception("Room number exists already !");
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
    public Room updateRoomInfor(Long roomNumber, UpdateRoomDTO room) throws Exception {
        Room roomToUpdate = getRoomByRoomNumber(roomNumber);
        roomToUpdate.setRoomType(room.getRoomType());
        roomToUpdate.setRoomPrice(room.getRoomPrice());
        return roomRepository.save(roomToUpdate);
    }

    @Override
    public Page<Room> getAllRooms(PageRequest PageRequest) throws Exception {
        
        Page<Room> rooms = roomRepository.findAll(PageRequest);

        if(rooms.isEmpty()) {
            throw new DataNotFoundException("No room found !");
        }

        return rooms;

    }

    @Override
    public Page<Room> getAvailableRooms(String type, PageRequest PageRequest) throws Exception {
        
        Page<Room> rooms = roomRepository.findAvailableRoom(type, PageRequest);

        if(rooms.isEmpty()) {
            throw new DataNotFoundException("No room found !");
        }

        return rooms;

    }

    @Override
    public Page<Room> getAvailableOrCleaningRooms(PageRequest PageRequest) throws Exception {
        
        Page<Room> rooms = roomRepository.findAvailableOrCleaningRoom(PageRequest);

        if(rooms.isEmpty()) {
            throw new DataNotFoundException("No room found !");
        }

        return rooms;

    }

    @Override
    public void deleteRoom(Long roomNumber) throws Exception {
        
        Room room = getRoomByRoomNumber(roomNumber);
        room.setRoomStatus("deleted");
        roomRepository.save(room);

    }

    @Override
    public Room getRoomByRoomNumber(Long roomNumber) throws Exception {
        
        Room room = roomRepository.findByRoomNumber(roomNumber);

        if(room == null) {
            throw new DataNotFoundException("Room not found !");
        }

        return room;

    }

}
