package ru.hotels.rgr.fulltest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.hotels.rgr.RgrApplication;
import ru.hotels.rgr.exception.HotelErrorCode;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RgrApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class FullTest extends TestBase {
    @Test
    public void testRegister() {
        clear();
        String cookieTest = registerUser(200, "SameLogin", null);
        registerUser(400, "SameLogin", HotelErrorCode.LOGIN_ALREADY_EXISTS);
        deleteUser(200, cookieTest, null);
        deleteUser(400, cookieTest, HotelErrorCode.USER_DELETED);
    }
}
