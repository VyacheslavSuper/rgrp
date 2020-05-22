package ru.hotels.rgr.service.test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import ru.hotels.rgr.dto.request.users.ChangePasswordRequest;
import ru.hotels.rgr.dto.request.users.LoginUserRequest;
import ru.hotels.rgr.dto.request.users.RegisterUserRequest;
import ru.hotels.rgr.dto.response.ResponseBase;
import ru.hotels.rgr.dto.response.users.FullUserResponse;
import ru.hotels.rgr.dto.response.users.ListFullUserResponse;
import ru.hotels.rgr.dto.response.users.UserResponse;
import ru.hotels.rgr.exception.HotelErrorCode;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.model.User;
import ru.hotels.rgr.model.types.UserType;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.fail;

public class TestUserS extends TestBase {
    @Test
    public void testCreateUser() throws HotelException {
        String cookie = "TEST_COOKIE";
        RegisterUserRequest request = new RegisterUserRequest("test_login", "test_name", "test@mail.ru", "password");
        UserResponse response = (UserResponse) userService.register(cookie, request);
        Assert.assertEquals(response.getEmail(), request.getEmail());
        Assert.assertEquals(response.getLogin(), request.getLogin());
        Assert.assertTrue(response.getId() > 0);
        try {
            userService.register(cookie, request);
            fail();
        } catch (HotelException hotelException) {
            Assert.assertEquals(HotelErrorCode.LOGIN_ALREADY_EXISTS, hotelException.getHotelErrorCode());
        }
    }

    @Test
    public void testSetAdmin() throws HotelException {
        User admin = new User("admin", "adminP", "Administrator", "admin@yandex.ru", UserType.ADMINISTRATOR, LocalDate.now());
        userDao.saveUser(admin);
        String adminCookie = "ADMIN_COOKIE";
        LoginUserRequest loginUserRequest = new LoginUserRequest(admin.getLogin(), admin.getPassword());
        sessionService.login(adminCookie, loginUserRequest);
        String cookie = "TEST_COOKIE";
        RegisterUserRequest request = new RegisterUserRequest("test_login", "test_name", "test@mail.ru", "password");
        UserResponse response = (UserResponse) userService.register(cookie, request);
        User before = userDao.findByLogin(response.getLogin());
        Assert.assertEquals(before.getUserType(), UserType.USER);
        userService.setAdmin(adminCookie, response.getId());
        User after = userDao.findByLogin(response.getLogin());
        Assert.assertEquals(after.getUserType(), UserType.ADMINISTRATOR);
    }

    @Test
    public void testGetUser() throws HotelException {
        User admin = new User("admin", "adminP", "Administrator", "admin@yandex.ru", UserType.ADMINISTRATOR, LocalDate.now());
        userDao.saveUser(admin);
        String adminCookie = "ADMIN_COOKIE";
        LoginUserRequest loginUserRequest = new LoginUserRequest(admin.getLogin(), admin.getPassword());
        UserResponse admResponse = (UserResponse) sessionService.login(adminCookie, loginUserRequest);
        String cookie = "TEST_COOKIE";
        RegisterUserRequest request = new RegisterUserRequest("test_login", "test_name", "test@mail.ru", "password");
        UserResponse response = (UserResponse) userService.register(cookie, request);
        FullUserResponse fullUserResponse = (FullUserResponse) userService.getUser(adminCookie, response.getId());
        Assert.assertEquals(response.getLogin(), fullUserResponse.getLogin());
        Assert.assertEquals(response.getEmail(), fullUserResponse.getEmail());

        try {
            userService.getUser(cookie, admResponse.getId());
            fail();
        } catch (HotelException he) {
            Assert.assertEquals(HotelErrorCode.NOT_ENOUGH_RIGHTS, he.getHotelErrorCode());
        }
    }

    @Test
    public void testGetUsers() throws HotelException {
        User admin = new User("admin", "adminP", "Administrator", "admin@yandex.ru", UserType.ADMINISTRATOR, LocalDate.now());
        userDao.saveUser(admin);
        String adminCookie = "ADMIN_COOKIE";
        LoginUserRequest loginUserRequest = new LoginUserRequest(admin.getLogin(), admin.getPassword());
        UserResponse admResponse = (UserResponse) sessionService.login(adminCookie, loginUserRequest);
        userService.register("TEST_COOKIE", new RegisterUserRequest("test_login", "test_name", "test@mail.ru", "password"));
        userService.register("TEST_COOKIE2", new RegisterUserRequest("test_login2", "test_name", "test@mail.ru", "password"));
        userService.register("TEST_COOKIE3", new RegisterUserRequest("test_login3", "test_name", "test@mail.ru", "password"));
        ListFullUserResponse listFullUserResponse = (ListFullUserResponse) userService.getUsers(adminCookie);
        Assert.assertEquals(4, listFullUserResponse.getList().size());
        try {
            userService.register("TEST_COOKIE3", new RegisterUserRequest("test_login4", "test_name", "test@mail.ru", "password"));
            fail();
        } catch (DataIntegrityViolationException he) {

        }
    }

    @Test
    public void testChangePass() throws HotelException {
        String cookie = "TEST_COOKIE";
        RegisterUserRequest request = new RegisterUserRequest("test_login", "test_name", "test@mail.ru", "password");
        UserResponse response = (UserResponse) userService.register(cookie, request);
        userService.changePassword(cookie, new ChangePasswordRequest(request.getPassword(), "NewPassword"));
        User user = userDao.findByLogin(response.getLogin());
        Assert.assertEquals(user.getPassword(), "NewPassword");
        try {
            userService.changePassword(cookie, new ChangePasswordRequest(request.getPassword(), "NewPassword"));
            fail();
        } catch (HotelException he) {
            Assert.assertEquals(HotelErrorCode.LOGIN_OR_PASSWORD_INVALID, he.getHotelErrorCode());
        }
    }

    @Test
    public void testDelete() throws HotelException {
        String cookie = "TEST_COOKIE";
        RegisterUserRequest request = new RegisterUserRequest("test_login", "test_name", "test@mail.ru", "password");
        UserResponse response = (UserResponse) userService.register(cookie, request);
        userService.deleteUser(cookie);
        User user = userDao.findByLogin(response.getLogin());
        Assert.assertTrue(user.getDeleted());
        try {
            userService.deleteUser(cookie);
            fail();
        } catch (HotelException he) {
            Assert.assertEquals(HotelErrorCode.USER_DELETED, he.getHotelErrorCode());
        }
    }

}
