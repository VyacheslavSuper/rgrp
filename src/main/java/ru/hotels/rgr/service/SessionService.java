package ru.hotels.rgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hotels.rgr.dto.request.users.LoginUserRequest;
import ru.hotels.rgr.dto.response.ResponseBase;
import ru.hotels.rgr.exception.HotelErrorCode;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.mapper.UserMapper;
import ru.hotels.rgr.model.Session;
import ru.hotels.rgr.model.User;

@Service
public class SessionService extends ServiceBase {
    @Autowired
    private UserMapper userMapper;

    public ResponseBase login(String cookie, LoginUserRequest request) throws HotelException {
        User user = findUserByLoginAndPassword(request.getLogin(), request.getPassword());
        if (isUserDeleted(user)) {
            throw new HotelException(HotelErrorCode.USER_DELETED);
        }
        Session session = new Session(cookie, user);
        sessionDao.deleteSessionByUser(user);
        sessionDao.saveSession(session);
        return userMapper.dtoFromUser(user);
    }

    public ResponseBase logout(String cookie) throws HotelException {
        Session session = sessionDao.findSessionByCookie(cookie);
        session.setUser(null);
        sessionDao.delete(session);
        return new ResponseBase();
    }

}
