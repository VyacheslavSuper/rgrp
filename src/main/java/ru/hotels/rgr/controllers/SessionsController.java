package ru.hotels.rgr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.hotels.rgr.dto.request.users.LoginUserRequest;
import ru.hotels.rgr.dto.response.ResponseBase;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.service.CookieService;
import ru.hotels.rgr.service.SessionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/sessions")
public class SessionsController {
    private SessionService sessionService;
    private CookieService cookieService;

    @Autowired
    public SessionsController(SessionService sessionService, CookieService cookieService) {
        this.sessionService = sessionService;
        this.cookieService = cookieService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase login(@Valid @RequestBody LoginUserRequest request, HttpServletResponse response) throws HotelException {
        Cookie cookie = cookieService.createCookie();
        ResponseBase responseBase = sessionService.login(cookie.getValue(), request);
        response.addCookie(cookie);
        return responseBase;
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase logout(@CookieValue(value = "SESSIONID") String cookie, HttpServletResponse response) throws HotelException {
        ResponseBase responseBase = sessionService.logout(cookie);
        response.addCookie(cookieService.deleteCookie());
        return responseBase;
    }
}
