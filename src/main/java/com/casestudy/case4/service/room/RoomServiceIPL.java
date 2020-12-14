package com.casestudy.case4.service.room;

import com.casestudy.case4.model.Room;
import com.casestudy.case4.repository.room.IRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RoomServiceIPL implements IRoomService{
    @Autowired
    private IRoomRepository iRoomRepository;

    @Override
    public Iterable<Room> findAll() {
        return iRoomRepository.findAll();
    }

    @Override
    public Room save(Room room) {
        return iRoomRepository.save(room);
    }

    @Override
    public Optional<Room> findById(Long id) {
        return iRoomRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
            iRoomRepository.deleteById(id);
    }


    @Override
    public Page<Room> findAllByHotelId(Long id, Pageable pageable) {
        return iRoomRepository.findAllByHotelId(id, pageable);
    }

    @Override
    public Room findAllById(Long id) {
        return iRoomRepository.findAllById(id);
    }
}
