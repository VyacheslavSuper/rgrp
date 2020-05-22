package ru.hotels.rgr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hotels.rgr.exception.HotelErrorCode;
import ru.hotels.rgr.exception.HotelException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse extends ResponseBase {
    private HotelErrorCode errorCode;
    private String message;

    public ErrorResponse(HotelException hotelException) {
        this(hotelException.getHotelErrorCode(), hotelException.getMessage());
    }
}
