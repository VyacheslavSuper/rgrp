package ru.hotels.rgr.service.test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import ru.hotels.rgr.dto.request.room.CreateRoomRequest;
import ru.hotels.rgr.dto.request.users.LoginUserRequest;
import ru.hotels.rgr.dto.response.rooms.ListRoomResponse;
import ru.hotels.rgr.dto.response.rooms.RoomResponse;
import ru.hotels.rgr.exception.HotelErrorCode;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.model.User;
import ru.hotels.rgr.model.types.UserType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class TestRoomS extends TestBase {

    @Test
    public void testCreateRoom() throws HotelException {
        User admin = new User("admin", "adminP", "Administrator", "admin@yandex.ru", UserType.ADMINISTRATOR, LocalDate.now());
        userDao.saveUser(admin);
        String adminCookie = "ADMIN_COOKIE";
        sessionService.login(adminCookie, new LoginUserRequest(admin.getLogin(), admin.getPassword()));

        List<String> list = new ArrayList<>();
        list.add("CoolTest");
        list.add("JustTest");
        RoomResponse roomResponse = (RoomResponse) roomService.createRoom(adminCookie, new CreateRoomRequest(1, 1000, "Very good room", list));
        Assert.assertEquals(roomResponse.getSpec().size(), list.size());
        Assert.assertEquals(roomResponse.getNumber(), 1);
        Assert.assertEquals(roomResponse.getPrice(), 1000);
    }

    @Test
    public void testGetRooms() throws HotelException {
        User admin = new User("admin", "adminP", "Administrator", "admin@yandex.ru", UserType.ADMINISTRATOR, LocalDate.now());
        userDao.saveUser(admin);
        String adminCookie = "ADMIN_COOKIE";
        sessionService.login(adminCookie, new LoginUserRequest(admin.getLogin(), admin.getPassword()));

        List<String> list = new ArrayList<>();
        list.add("CoolTest");
        list.add("JustTest");
        roomService.createRoom(adminCookie, new CreateRoomRequest(1, 1000, "Very good room", list));
        roomService.createRoom(adminCookie, new CreateRoomRequest(2, 1000, "Very good room", list));
        roomService.createRoom(adminCookie, new CreateRoomRequest(3, 1000, "Very good room", list));

        ListRoomResponse listRoomResponse = (ListRoomResponse) roomService.getRooms(adminCookie, 0, 10);
        Assert.assertEquals(listRoomResponse.getList().size(), 3);
    }

    @Test
    public void testDeleteRoom() throws HotelException {
        User admin = new User("admin", "adminP", "Administrator", "admin@yandex.ru", UserType.ADMINISTRATOR, LocalDate.now());
        userDao.saveUser(admin);
        String adminCookie = "ADMIN_COOKIE";
        sessionService.login(adminCookie, new LoginUserRequest(admin.getLogin(), admin.getPassword()));

        List<String> list = new ArrayList<>();
        list.add("CoolTest");
        list.add("JustTest");
        RoomResponse roomResponse = (RoomResponse) roomService.createRoom(adminCookie, new CreateRoomRequest(1, 1000, "Very good room", list));
        roomService.deleteRoom(roomResponse.getId(), adminCookie);
        try {
            roomService.deleteRoom(roomResponse.getId(), adminCookie);
            fail();
        } catch (HotelException he) {
            Assert.assertEquals(HotelErrorCode.ROOM_NOT_FOUND, he.getHotelErrorCode());
        }
    }

}
