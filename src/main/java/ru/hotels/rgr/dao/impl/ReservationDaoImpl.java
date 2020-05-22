package ru.hotels.rgr.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.hotels.rgr.dao.ReservationDao;
import ru.hotels.rgr.dao.repository.ReservationRepository;
import ru.hotels.rgr.exception.HotelErrorCode;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.model.Reservation;
import ru.hotels.rgr.model.User;

import java.util.List;
import java.util.Optional;

@Component
public class ReservationDaoImpl implements ReservationDao {
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation findById(long id) throws HotelException {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new HotelException(HotelErrorCode.RESERVATION_NOT_FOUND));
    }

    @Override
    public List<Reservation> findByUser(User user) {
        return reservationRepository.findByUser(user);
    }

    @Override
    public List<Reservation> findAll(int offset, int limit) {
        Pageable limitRule = PageRequest.of(offset, limit);
        return reservationRepository.findAll(limitRule).getContent();
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public void delete(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    @Override
    public void deleteAll() {
        reservationRepository.deleteAll();
    }
}
