package ru.hotels.rgr.service.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.hotels.rgr.dao.*;
import ru.hotels.rgr.dto.request.RequestBase;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.service.*;

import static org.junit.Assert.fail;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TestBase {

    @Autowired
    protected UserService userService;
    @Autowired
    protected SessionService sessionService;
    @Autowired
    protected RoomService roomService;
    @Autowired
    protected ReservationService reservationService;
    @Autowired
    protected DebugService debugService;
    @Autowired
    protected SpecificationService specificationService;

    @SpyBean
    protected UserDao userDao;
    @SpyBean
    protected SessionDao sessionDao;
    @SpyBean
    protected SpecificationDao specificationDao;
    @SpyBean
    protected RoomDao roomDao;
    @SpyBean
    protected ReservationDao reservationDao;

    @BeforeEach
    public void clearDB() {
        try {
            debugService.clear(new RequestBase());
        } catch (HotelException hotelException) {
            fail();
        }
    }
}
