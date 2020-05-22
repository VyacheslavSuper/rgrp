package ru.hotels.rgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hotels.rgr.dao.*;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.model.Session;
import ru.hotels.rgr.model.User;
import ru.hotels.rgr.model.types.UserType;

@Transactional(rollbackFor = HotelException.class)
@Service
public class ServiceBase {

    @Autowired
    protected UserDao userDao;
    @Autowired
    protected SessionDao sessionDao;
    @Autowired
    protected SpecificationDao specificationDao;
    @Autowired
    protected RoomDao roomDao;
    @Autowired
    protected ReservationDao reservationDao;
    @Autowired
    protected SpecificationService specificationService;


    protected User findUserByLoginAndPassword(String login, String password) throws HotelException {
        return userDao.findByLoginAndPassword(login, password);
    }

    protected User findUserById(long id) throws HotelException {
        return userDao.findById(id);
    }

    protected User findUserByLogin(String login) throws HotelException {
        return userDao.findByLogin(login);
    }

    protected Session findSessionByCookie(String cookie) throws HotelException {
        return sessionDao.findSessionByCookie(cookie);
    }

    protected boolean isAdminBySession(Session session) {
        return isAdminByUser(session.getUser());
    }

    protected boolean isAdminByUser(User user) {
        return user.getUserType().equals(UserType.ADMINISTRATOR);
    }

    protected boolean isUserDeleted(User user) {
        return user.getDeleted().equals(true);
    }
}
