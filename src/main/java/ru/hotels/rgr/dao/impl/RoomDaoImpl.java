package ru.hotels.rgr.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.hotels.rgr.dao.RoomDao;
import ru.hotels.rgr.dao.repository.RoomRepository;
import ru.hotels.rgr.exception.HotelErrorCode;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.model.Room;

import java.util.List;
import java.util.Optional;

@Component
public class RoomDaoImpl implements RoomDao {
    @Autowired
    private RoomRepository roomRepository;


    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room findById(long id) throws HotelException {
        return roomRepository.findById(id)
                .orElseThrow(() -> new HotelException(HotelErrorCode.ROOM_NOT_FOUND));
    }

    @Override
    public Room findByNumber(int number) throws HotelException {
        return roomRepository.findByNumber(number)
                .orElseThrow(() -> new HotelException(HotelErrorCode.ROOM_NOT_FOUND));
    }

    @Override
    public List<Room> findAll(int offset, int limit) {
        Pageable limitRule = PageRequest.of(offset, limit);
        return roomRepository.findAll(limitRule).getContent();
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public void delete(Room room) {
        roomRepository.delete(room);
    }

    @Override
    public void deleteAll() {
        roomRepository.deleteAll();
    }
}
