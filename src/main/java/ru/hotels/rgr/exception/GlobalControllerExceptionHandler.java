package ru.hotels.rgr.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hotels.rgr.dto.response.ErrorResponse;
import ru.hotels.rgr.dto.response.ListErrorResponse;
import ru.hotels.rgr.dto.response.ResponseBase;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseBase dataBaseError() {
        return new ErrorResponse(new HotelException(HotelErrorCode.DATABASE_ERROR));
    }

    @ExceptionHandler(HotelException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseBase handleForumError(HttpServletRequest req, HotelException exception) {
        return new ErrorResponse(exception);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseBase handleNullPointerError() {
        return new ErrorResponse(new HotelException(HotelErrorCode.BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseBase handleNotValidError(MethodArgumentNotValidException exception) {
        List<ErrorResponse> errors = new ArrayList<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errors.add(new ErrorResponse(HotelErrorCode.BAD_REQUEST, fieldError.getDefaultMessage()));
        }
        return new ListErrorResponse(errors);
    }

}
