package ru.hotels.rgr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hotels.rgr.dto.request.RequestBase;
import ru.hotels.rgr.dto.response.ResponseBase;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.service.DebugService;

@RestController
@RequestMapping("/api/debug")
public class DebugController {
    @Autowired
    private DebugService debugService;

    @PostMapping(value = "/clear", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase clearAll(@RequestBody RequestBase request) throws HotelException {
        return debugService.clear(request);
    }
}
