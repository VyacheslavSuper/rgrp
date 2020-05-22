package ru.hotels.rgr.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hotels.rgr.dto.request.RequestBase;
import ru.hotels.rgr.dto.response.ResponseBase;
import ru.hotels.rgr.exception.HotelException;

@Service
public class DebugService extends ServiceBase {

    public ResponseBase clear(RequestBase request) throws HotelException {
        reservationDao.deleteAll();
        sessionDao.deleteAll();
        userDao.deleteAll();
        specificationDao.deleteAll();
        roomDao.deleteAll();
        return new ResponseBase();
    }
}
