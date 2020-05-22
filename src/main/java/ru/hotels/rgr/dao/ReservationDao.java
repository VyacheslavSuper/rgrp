package ru.hotels.rgr.dao;

import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.model.Reservation;
import ru.hotels.rgr.model.Room;
import ru.hotels.rgr.model.User;

import java.util.List;
import java.util.Optional;

public interface ReservationDao {

    Reservation save(Reservation reservation);

    Reservation findById(long id) throws HotelException;

    List<Reservation> findByUser(User user);

    List<Reservation> findAll(int offset, int limit);

    List<Reservation> findAll();

    void delete(Reservation reservation);

    void deleteAll();

}
