package ru.hotels.rgr.dao;

import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.model.Session;
import ru.hotels.rgr.model.User;

import java.util.Optional;

public interface SessionDao {

    void deleteSessionByUser(User user);

    void addNewSession(Session session);

    void delete(Session session);

    void saveSession(Session session);

    Session findSessionByCookie(String cookie) throws HotelException;

    Session findByUser(User user);

    void deleteAll();

}
