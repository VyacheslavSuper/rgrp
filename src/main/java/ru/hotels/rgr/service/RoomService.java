package ru.hotels.rgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hotels.rgr.dto.request.room.CreateRoomRequest;
import ru.hotels.rgr.dto.response.ResponseBase;
import ru.hotels.rgr.dto.response.rooms.ListRoomResponse;
import ru.hotels.rgr.dto.response.rooms.RoomResponse;
import ru.hotels.rgr.exception.HotelErrorCode;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.mapper.RoomMapper;
import ru.hotels.rgr.model.Room;
import ru.hotels.rgr.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService extends ServiceBase {
    @Autowired
    private RoomMapper roomMapper;

    public ResponseBase createRoom(String cookie, CreateRoomRequest request) throws HotelException {
        User user = findSessionByCookie(cookie).getUser();
        if (isUserDeleted(user)) {
            throw new HotelException(HotelErrorCode.USER_DELETED);
        }
        if (!isAdminByUser(user)) {
            throw new HotelException(HotelErrorCode.NOT_ENOUGH_RIGHTS);
        }
        Room room = roomMapper.roomFromDto(request);
        room.setSpecifications(specificationService.createSpecificationListFromStringList(request.getSpecs()));
        room.setUser(user);
        roomDao.save(room);
        return getRoom(room.getId(),cookie);
    }

    public ResponseBase getRooms(String cookie, int offset, int limit) {
        List<Room> list = roomDao.findAll(offset, limit);
        List<RoomResponse> responseList = new ArrayList<>();
        list.forEach(room -> {
            RoomResponse roomResponse = roomMapper.dtoFromResponse(room);
            roomResponse.setSpec(specificationService.createStringListFromSpecificationList(room.getSpecifications()));
            responseList.add(roomResponse);
        });
        return new ListRoomResponse(responseList);
    }

    public ResponseBase getRoom(long id, String cookie) throws HotelException {
        Room room = roomDao.findById(id);
        RoomResponse roomResponse = roomMapper.dtoFromResponse(room);
        roomResponse.setSpec(specificationService.createStringListFromSpecificationList(room.getSpecifications()));
        return roomResponse;
    }

    public ResponseBase deleteRoom(long id, String cookie) throws HotelException {
        User user = findSessionByCookie(cookie).getUser();
        if (isUserDeleted(user)) {
            throw new HotelException(HotelErrorCode.USER_DELETED);
        }
        if (!isAdminByUser(user)) {
            throw new HotelException(HotelErrorCode.NOT_ENOUGH_RIGHTS);
        }
        Room room = roomDao.findById(id);
        room.setUser(null);
        room.getSpecifications().forEach(specification -> specificationDao.delete(specification));
        room.getReservations().forEach(reservation -> reservationDao.delete(reservation));
        room.setSpecifications(null);
        room.setReservations(null);
        roomDao.delete(room);
        return new ResponseBase();
    }
}
