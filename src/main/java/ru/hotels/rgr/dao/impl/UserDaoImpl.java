package ru.hotels.rgr.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.hotels.rgr.dao.UserDao;
import ru.hotels.rgr.dao.repository.UserRepository;
import ru.hotels.rgr.exception.HotelErrorCode;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(long id) throws HotelException {
        return userRepository.findById(id)
                .orElseThrow(() -> new HotelException(HotelErrorCode.USER_ID_NOT_FOUND));
    }

    @Override
    public User findByLogin(String login) throws HotelException {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new HotelException(HotelErrorCode.LOGIN_NOT_FOUND));
    }

    @Override
    public User findByLoginWithoutException(String login) {
        return userRepository.findByLogin(login).orElse(null);
    }

    @Override
    public User findByLoginAndPassword(String name, String password) throws HotelException {
        return userRepository.findByLoginAndPassword(name, password)
                .orElseThrow(() -> new HotelException(HotelErrorCode.LOGIN_OR_PASSWORD_INVALID));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
