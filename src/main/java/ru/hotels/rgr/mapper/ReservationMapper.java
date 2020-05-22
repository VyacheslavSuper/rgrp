package ru.hotels.rgr.mapper;

import org.mapstruct.Mapper;
import ru.hotels.rgr.dto.request.reservation.CreateReservationRequest;
import ru.hotels.rgr.dto.response.reservations.ReservationResponse;
import ru.hotels.rgr.model.Reservation;

@Mapper
public interface ReservationMapper {
    ReservationResponse dtoFromReservation(Reservation reservation);

    Reservation reservationFromDto(CreateReservationRequest reservationRequest);
}
