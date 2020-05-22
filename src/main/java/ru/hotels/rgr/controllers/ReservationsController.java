package ru.hotels.rgr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.hotels.rgr.dto.request.reservation.ConfirmReservationRequest;
import ru.hotels.rgr.dto.request.reservation.CreateReservationRequest;
import ru.hotels.rgr.dto.response.ResponseBase;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.service.ReservationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/reservation")
public class ReservationsController {

    @Autowired
    private ReservationService reservationService;

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase deleteReservation(@PathVariable("id") int id, @CookieValue(value = "SESSIONID") String cookie) throws HotelException {
        return reservationService.deleteReservation(cookie, id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase getReservation(@PathVariable("id") int id, @CookieValue(value = "SESSIONID") String cookie) throws HotelException {
        return reservationService.getReservation(id, cookie);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase getReservations(
            @CookieValue(value = "SESSIONID") String cookie,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "100") int limit) throws HotelException {
        return reservationService.getReservations(cookie, offset, limit);
    }

    @PutMapping(value = "/{id}/confirm", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase confirmReservation(@PathVariable("id") int id, @CookieValue(value = "SESSIONID") String cookie, @RequestBody @Valid ConfirmReservationRequest request) throws HotelException {
        return reservationService.confirmReservation(id, cookie, request);
    }

}
