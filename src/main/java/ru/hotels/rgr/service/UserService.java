package ru.hotels.rgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hotels.rgr.dto.request.users.ChangePasswordRequest;
import ru.hotels.rgr.dto.request.users.RegisterUserRequest;
import ru.hotels.rgr.dto.response.ResponseBase;
import ru.hotels.rgr.dto.response.users.FullUserResponse;
import ru.hotels.rgr.dto.response.users.ListFullUserResponse;
import ru.hotels.rgr.exception.HotelErrorCode;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.mapper.UserMapper;
import ru.hotels.rgr.model.Session;
import ru.hotels.rgr.model.User;
import ru.hotels.rgr.model.types.UserType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends ServiceBase {
    @Autowired
    private UserMapper userMapper;

    public ResponseBase register(String cookie, RegisterUserRequest request) throws HotelException {
        User find = userDao.findByLoginWithoutException(request.getLogin());
        if (find != null) {
            throw new HotelException(HotelErrorCode.LOGIN_ALREADY_EXISTS, find.getLogin());
        }
        User user = userMapper.userFromDto(request);
        user.setTimeRegistered(LocalDate.now());
        userDao.saveUser(user);
        Session session = new Session(cookie, user);
        sessionDao.saveSession(session);
        return userMapper.dtoFromUser(user);
    }

    public ResponseBase setAdmin(String cookie, long id) throws HotelException {
        User admin = findSessionByCookie(cookie).getUser();
        if (isUserDeleted(admin)) {
            throw new HotelException(HotelErrorCode.USER_DELETED);
        }
        if (!isAdminByUser(admin)) {
            throw new HotelException(HotelErrorCode.NOT_ENOUGH_RIGHTS);
        }
        User user = findUserById(id);
        if (isAdminByUser(user)) {
            throw new HotelException(HotelErrorCode.ALREADY_ADMIN);
        }
        user.setUserType(UserType.ADMINISTRATOR);
        userDao.saveUser(user);
        return new ResponseBase();
    }

    public ResponseBase getUser(String cookie, long id) throws HotelException {
        User admin = findSessionByCookie(cookie).getUser();
        if (isUserDeleted(admin)) {
            throw new HotelException(HotelErrorCode.USER_DELETED);
        }
        if (!isAdminByUser(admin)) {
            throw new HotelException(HotelErrorCode.NOT_ENOUGH_RIGHTS);
        }
        User user = findUserById(id);
        return userMapper.fullDtoFromUser(user);
    }

    public ResponseBase getUsers(String cookie) throws HotelException {
        User user = findSessionByCookie(cookie).getUser();
        if (isUserDeleted(user)) {
            throw new HotelException(HotelErrorCode.USER_DELETED);
        }
        if (!isAdminByUser(user)) {
            throw new HotelException(HotelErrorCode.NOT_ENOUGH_RIGHTS);
        }
        List<FullUserResponse> list = new ArrayList<>();
        userDao.findAll().forEach(u -> {
            list.add(userMapper.fullDtoFromUser(u));
        });
        return new ListFullUserResponse(list);
    }

    public ResponseBase changePassword(String cookie, ChangePasswordRequest request) throws HotelException {
        User user = findSessionByCookie(cookie).getUser();
        if (isUserDeleted(user)) {
            throw new HotelException(HotelErrorCode.USER_DELETED);
        }
        if (!user.getPassword().equals(request.getOldPassword())) {
            throw new HotelException(HotelErrorCode.LOGIN_OR_PASSWORD_INVALID);
        }
        user.setPassword(request.getPassword());
        userDao.saveUser(user);
        return new ResponseBase();
    }

    public ResponseBase deleteUser(String cookie) throws HotelException {
        User user = findSessionByCookie(cookie).getUser();
        if (isUserDeleted(user)) {
            throw new HotelException(HotelErrorCode.USER_DELETED);
        }
        user.setDeleted(true);
        userDao.saveUser(user);
        return new ResponseBase();
    }


}
