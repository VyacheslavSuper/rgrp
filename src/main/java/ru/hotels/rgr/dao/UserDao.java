package ru.hotels.rgr.dao;

import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    void saveUser(User user);

    User findById(long id) throws HotelException;

    User findByLogin(String login) throws HotelException;

    User findByLoginWithoutException(String login);

    User findByLoginAndPassword(String name, String password) throws HotelException;

    List<User> findAll();

    void deleteAll();
}
