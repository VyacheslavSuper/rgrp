package ru.hotels.rgr.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.hotels.rgr.dao.SessionDao;
import ru.hotels.rgr.dao.repository.SessionRepository;
import ru.hotels.rgr.exception.HotelErrorCode;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.model.Session;
import ru.hotels.rgr.model.User;

import java.util.Optional;
@Component
public class SessionDaoImpl  implements SessionDao {
    @Autowired
    private SessionRepository sessionRepository;


    @Override
    public void deleteSessionByUser(User user) {
        sessionRepository.deleteSessionByUser(user);
    }

    @Override
    public void addNewSession(Session session) {
        sessionRepository.save(session);
    }

    @Override
    public void delete(Session session) {
        sessionRepository.delete(session);
    }

    @Override
    public void saveSession(Session session) {
        sessionRepository.save(session);
    }

    @Override
    public Session findSessionByCookie(String cookie) throws HotelException {
        return sessionRepository.findByCookie(cookie)
                .orElseThrow(() -> new HotelException(HotelErrorCode.USER_UNAUTHORIZED));
    }

    @Override
    public Session findByUser(User user) {
        return sessionRepository.findByUser(user);
    }

    @Override
    public void deleteAll() {
        sessionRepository.deleteAll();
    }
}
