package ru.hotels.rgr.dto.response.reservations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hotels.rgr.dto.response.ResponseBase;
import ru.hotels.rgr.model.types.ReservationType;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse extends ResponseBase {
    private int id;
    private LocalDate timeStart;
    private LocalDate timeEnd;
    private ReservationType type;
}
