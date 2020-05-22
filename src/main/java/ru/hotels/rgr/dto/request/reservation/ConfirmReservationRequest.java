package ru.hotels.rgr.dto.request.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hotels.rgr.model.types.ReservationType;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmReservationRequest {
    @NotNull
    private ReservationType type;
}
