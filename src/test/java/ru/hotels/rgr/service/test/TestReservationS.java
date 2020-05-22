package ru.hotels.rgr.service.test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import ru.hotels.rgr.dto.request.reservation.ConfirmReservationRequest;
import ru.hotels.rgr.dto.request.reservation.CreateReservationRequest;
import ru.hotels.rgr.dto.request.room.CreateRoomRequest;
import ru.hotels.rgr.dto.request.users.LoginUserRequest;
import ru.hotels.rgr.dto.request.users.RegisterUserRequest;
import ru.hotels.rgr.dto.response.reservations.ListReservationResponse;
import ru.hotels.rgr.dto.response.reservations.ReservationResponse;
import ru.hotels.rgr.dto.response.rooms.RoomResponse;
import ru.hotels.rgr.dto.response.users.UserResponse;
import ru.hotels.rgr.exception.HotelErrorCode;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.model.User;
import ru.hotels.rgr.model.types.ReservationType;
import ru.hotels.rgr.model.types.UserType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class TestReservationS extends TestBase {

    @Test
    public void testCreateReservation() throws HotelException {
        String cookie = "TEST_COOKIE";
        int idRoom = createRoom();
        RegisterUserRequest request = new RegisterUserRequest("test_login", "test_name", "test@mail.ru", "password");
        userService.register(cookie, request);
        CreateReservationRequest createReservationRequest = new CreateReservationRequest(LocalDate.now(), LocalDate.now().plusDays(5));
        ReservationResponse response = (ReservationResponse) reservationService.createReservation(idRoom, cookie, createReservationRequest);
        Assert.assertEquals(response.getType(), ReservationType.IN_PROGRESS);
    }

    @Test
    public void testGetReservation() throws HotelException {
        String cookie = "TEST_COOKIE";
        int idRoom = createRoom();
        RegisterUserRequest request = new RegisterUserRequest("test_login", "test_name", "test@mail.ru", "password");
        userService.register(cookie, request);
        CreateReservationRequest createReservationRequest = new CreateReservationRequest(LocalDate.now(), LocalDate.now().plusDays(5));
        ReservationResponse response = (ReservationResponse) reservationService.createReservation(idRoom, cookie, createReservationRequest);

        String adminCookie = "ADMIN_COOKIE";
        ReservationResponse responseForAdmin = (ReservationResponse) reservationService.getReservation(response.getId(), adminCookie);
        Assert.assertEquals(response.getTimeStart(), responseForAdmin.getTimeStart());
        Assert.assertEquals(response.getTimeEnd(), responseForAdmin.getTimeEnd());
    }

    @Test
    public void testGetReservations() throws HotelException {
        String cookie = "TEST_COOKIE";
        int idRoom = createRoom();
        RegisterUserRequest request = new RegisterUserRequest("test_login", "test_name", "test@mail.ru", "password");
        userService.register(cookie, request);

        reservationService.createReservation(idRoom, cookie, new CreateReservationRequest(LocalDate.now(), LocalDate.now().plusDays(1)));
        reservationService.createReservation(idRoom, cookie, new CreateReservationRequest(LocalDate.now().plusDays(2), LocalDate.now().plusDays(3)));
        reservationService.createReservation(idRoom, cookie, new CreateReservationRequest(LocalDate.now().plusDays(4), LocalDate.now().plusDays(5)));

        String adminCookie = "ADMIN_COOKIE";
        ListReservationResponse responseForAdmin = (ListReservationResponse) reservationService.getReservations(adminCookie,0,10);
        Assert.assertEquals(responseForAdmin.getList().size(),3);
        ListReservationResponse responseForAdmin2 = (ListReservationResponse) reservationService.getReservations(adminCookie,0,1);
        Assert.assertEquals(responseForAdmin2.getList().size(),1);
    }

    @Test
    public void testDeleteReservation() throws HotelException {
        String cookie = "TEST_COOKIE";
        int idRoom = createRoom();
        RegisterUserRequest request = new RegisterUserRequest("test_login", "test_name", "test@mail.ru", "password");
        userService.register(cookie, request);

        ReservationResponse response = (ReservationResponse) reservationService.createReservation(idRoom, cookie, new CreateReservationRequest(LocalDate.now(), LocalDate.now().plusDays(1)));

        String adminCookie = "ADMIN_COOKIE";
        reservationService.deleteReservation(adminCookie,response.getId());

        try{
            reservationService.deleteReservation(adminCookie,response.getId());
            fail();
        } catch (HotelException hotelException) {
            Assert.assertEquals(HotelErrorCode.RESERVATION_NOT_FOUND, hotelException.getHotelErrorCode());
        }
    }

    @Test
    public void testConfirmReservation() throws HotelException {
        String cookie = "TEST_COOKIE";
        int idRoom = createRoom();
        RegisterUserRequest request = new RegisterUserRequest("test_login", "test_name", "test@mail.ru", "password");
        userService.register(cookie, request);

        ReservationResponse response = (ReservationResponse) reservationService.createReservation(idRoom, cookie, new CreateReservationRequest(LocalDate.now(), LocalDate.now().plusDays(1)));

        String adminCookie = "ADMIN_COOKIE";
        reservationService.confirmReservation(response.getId(),adminCookie,new ConfirmReservationRequest(ReservationType.COMPLETED));
        ReservationResponse responseForAdmin = (ReservationResponse) reservationService.getReservation(response.getId(), adminCookie);
        Assert.assertEquals(responseForAdmin.getType(),ReservationType.COMPLETED);

    }


    private int createRoom() throws HotelException {
        User admin = new User("admin", "adminP", "Administrator", "admin@yandex.ru", UserType.ADMINISTRATOR, LocalDate.now());
        userDao.saveUser(admin);
        String adminCookie = "ADMIN_COOKIE";
        sessionService.login(adminCookie, new LoginUserRequest(admin.getLogin(), admin.getPassword()));

        List<String> list = new ArrayList<>();
        list.add("CoolTest");
        list.add("JustTest");
        RoomResponse roomResponse = (RoomResponse) roomService.createRoom(adminCookie, new CreateRoomRequest(1, 1000, "Very good room", list));
        return roomResponse.getId();
    }
}
