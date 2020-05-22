package ru.hotels.rgr.dto.request.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationRequest {
    @NotNull
    private LocalDate timeStart;
    @NotNull
    private LocalDate timeEnd;
}
