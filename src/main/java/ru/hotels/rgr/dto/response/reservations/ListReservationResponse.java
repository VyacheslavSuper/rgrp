package ru.hotels.rgr.dto.response.reservations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hotels.rgr.dto.response.ResponseBase;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListReservationResponse extends ResponseBase {
    private List<ReservationResponse> list;
}
