package ru.hotels.rgr.exception;

import lombok.Data;

@Data
public class HotelException extends Exception {
    private HotelErrorCode hotelErrorCode;

    public HotelException(HotelErrorCode hotelErrorCode,String message) {
        this.hotelErrorCode = hotelErrorCode;
        if (message != null && message.length() > 0) {
            hotelErrorCode.setMessage(String.format(hotelErrorCode.getMessage(), message));
        }
    }

    public HotelException(HotelErrorCode hotelErrorCode) {
        this(hotelErrorCode, null);
    }

    public HotelErrorCode getError() {
        return hotelErrorCode;
    }

}
