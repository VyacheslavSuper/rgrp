package ru.hotels.rgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hotels.rgr.dto.request.reservation.ConfirmReservationRequest;
import ru.hotels.rgr.dto.request.reservation.CreateReservationRequest;
import ru.hotels.rgr.dto.response.ResponseBase;
import ru.hotels.rgr.dto.response.reservations.ListReservationResponse;
import ru.hotels.rgr.dto.response.reservations.ReservationResponse;
import ru.hotels.rgr.exception.HotelErrorCode;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.mapper.ReservationMapper;
import ru.hotels.rgr.model.Reservation;
import ru.hotels.rgr.model.Room;
import ru.hotels.rgr.model.User;
import ru.hotels.rgr.model.types.ReservationType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService extends ServiceBase {
    @Autowired
    private ReservationMapper reservationMapper;

    public ResponseBase createReservation(int id, String cookie, CreateReservationRequest request) throws HotelException {
        User user = findSessionByCookie(cookie).getUser();
        if (isUserDeleted(user)) {
            throw new HotelException(HotelErrorCode.USER_DELETED);
        }
        Room room = roomDao.findById(id);
        Reservation reservation = reservationMapper.reservationFromDto(request);
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation = reservationDao.save(reservation);
        roomDao.save(room);
        return reservationMapper.dtoFromReservation(reservation);
    }

    public ResponseBase deleteReservation(String cookie, int id) throws HotelException {
        User user = findSessionByCookie(cookie).getUser();
        if (isUserDeleted(user)) {
            throw new HotelException(HotelErrorCode.USER_DELETED);
        }
        if (!isAdminByUser(user)) {
            throw new HotelException(HotelErrorCode.NOT_ENOUGH_RIGHTS);
        }
        Reservation reservation = reservationDao.findById(id);
        reservation.setUser(null);
        reservation.setRoom(null);
        reservationDao.delete(reservation);
        return new ResponseBase();
    }

    public ResponseBase getReservation(int id, String cookie) throws HotelException {
        User user = findSessionByCookie(cookie).getUser();
        if (isUserDeleted(user)) {
            throw new HotelException(HotelErrorCode.USER_DELETED);
        }
        if (!isAdminByUser(user)) {
            throw new HotelException(HotelErrorCode.NOT_ENOUGH_RIGHTS);
        }
        Reservation reservation = reservationDao.findById(id);
        return reservationMapper.dtoFromReservation(reservation);
    }

    public ResponseBase getReservations(String cookie, int offset, int limit) throws HotelException {
        User user = findSessionByCookie(cookie).getUser();
        if (isUserDeleted(user)) {
            throw new HotelException(HotelErrorCode.USER_DELETED);
        }
        if (!isAdminByUser(user)) {
            throw new HotelException(HotelErrorCode.NOT_ENOUGH_RIGHTS);
        }
        List<Reservation> list = reservationDao.findAll(offset, limit);
        List<ReservationResponse> responseList = new ArrayList<>();
        list.forEach(reservation -> responseList.add(reservationMapper.dtoFromReservation(reservation)));
        return new ListReservationResponse(responseList);
    }

    public ResponseBase confirmReservation(int id, String cookie, ConfirmReservationRequest request) throws HotelException {
        User user = findSessionByCookie(cookie).getUser();
        if (isUserDeleted(user)) {
            throw new HotelException(HotelErrorCode.USER_DELETED);
        }
        if (!isAdminByUser(user)) {
            throw new HotelException(HotelErrorCode.NOT_ENOUGH_RIGHTS);
        }
        Reservation reservation = reservationDao.findById(id);
        reservation.setType(request.getType());
        reservationDao.save(reservation);
        return new ResponseBase();
    }
}
