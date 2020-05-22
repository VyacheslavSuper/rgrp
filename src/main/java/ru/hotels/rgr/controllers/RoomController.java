package ru.hotels.rgr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.hotels.rgr.dto.request.reservation.CreateReservationRequest;
import ru.hotels.rgr.dto.request.room.CreateRoomRequest;
import ru.hotels.rgr.dto.response.ResponseBase;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.service.ReservationService;
import ru.hotels.rgr.service.RoomService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private ReservationService reservationService;

    @PostMapping(value = "/{id}/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase createReservation(@PathVariable("id") int id, @CookieValue(value = "SESSIONID") String cookie, @RequestBody @Valid CreateReservationRequest request) throws HotelException {
        return reservationService.createReservation(id, cookie, request);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase createRoom(@CookieValue(value = "SESSIONID") String cookie, @RequestBody @Valid CreateRoomRequest request) throws HotelException {
        return roomService.createRoom(cookie, request);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase deleteRoom(@PathVariable("id") int id, @CookieValue(value = "SESSIONID") String cookie) throws HotelException {
        return roomService.deleteRoom(id, cookie);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase getRoom(@PathVariable("id") int id, @CookieValue(value = "SESSIONID") String cookie) throws HotelException {
        return roomService.getRoom(id, cookie);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase getRooms(
            @CookieValue(value = "SESSIONID") String cookie,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "100") int limit) throws HotelException {
        return roomService.getRooms(cookie, offset, limit);
    }

}
