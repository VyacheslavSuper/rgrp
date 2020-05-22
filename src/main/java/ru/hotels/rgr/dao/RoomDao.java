package ru.hotels.rgr.dao;

import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomDao {
    Room save(Room room);

    Room findById(long id) throws HotelException;

    Room findByNumber(int number) throws HotelException;

    List<Room> findAll(int offset, int limit);

    List<Room> findAll();

    void delete(Room room);

    void deleteAll();
}
