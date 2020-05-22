package ru.hotels.rgr.service.test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import ru.hotels.rgr.dto.request.users.LoginUserRequest;
import ru.hotels.rgr.dto.request.users.RegisterUserRequest;
import ru.hotels.rgr.dto.response.users.UserResponse;
import ru.hotels.rgr.exception.HotelErrorCode;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.model.User;
import ru.hotels.rgr.model.types.UserType;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.fail;

public class TestSessionS extends TestBase{
    @Test
    public void testLogin() throws HotelException {
        User admin = new User("admin", "adminP", "Administrator", "admin@yandex.ru", UserType.ADMINISTRATOR, LocalDate.now());
        userDao.saveUser(admin);
        String adminCookie = "ADMIN_COOKIE";
        LoginUserRequest loginUserRequest = new LoginUserRequest(admin.getLogin(), admin.getPassword());
        sessionService.login(adminCookie, loginUserRequest);
        sessionService.logout(adminCookie);
        try{
            sessionService.logout(adminCookie);
            fail();
        } catch (HotelException he) {
            Assert.assertEquals(HotelErrorCode.USER_UNAUTHORIZED, he.getHotelErrorCode());
        }
    }
}
