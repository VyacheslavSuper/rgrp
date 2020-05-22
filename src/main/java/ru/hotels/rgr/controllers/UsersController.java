package ru.hotels.rgr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.hotels.rgr.dto.request.users.ChangePasswordRequest;
import ru.hotels.rgr.dto.request.users.RegisterUserRequest;
import ru.hotels.rgr.dto.response.ResponseBase;
import ru.hotels.rgr.exception.HotelException;
import ru.hotels.rgr.service.CookieService;
import ru.hotels.rgr.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private UserService userService;
    private CookieService cookieService;

    @Autowired
    public UsersController(UserService userService, CookieService cookieService) {
        this.userService = userService;
        this.cookieService = cookieService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase registrationUser(@Valid @RequestBody RegisterUserRequest request, HttpServletResponse response) throws HotelException {
        Cookie cookie = cookieService.createCookie();
        ResponseBase responseBase = userService.register(cookie.getValue(), request);
        response.addCookie(cookie);
        return responseBase;
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase deleteUser(@CookieValue(value = "SESSIONID") String cookie, HttpServletResponse response) throws HotelException {
        ResponseBase responseBase = userService.deleteUser(cookie);
        response.addCookie(cookieService.deleteCookie());
        return responseBase;
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase changePassword(@CookieValue(value = "SESSIONID") String cookie, @RequestBody @Valid ChangePasswordRequest request, HttpServletResponse response) throws HotelException {
        return userService.changePassword(cookie, request);
    }

    @PutMapping(value = "/{id}/super", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase setAdmin(@PathVariable("id") int id, @CookieValue(value = "SESSIONID") String cookie) throws HotelException {
        return userService.setAdmin(cookie, id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase getUsers(@CookieValue(value = "SESSIONID") String cookie) throws HotelException {
        return userService.getUsers(cookie);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase getUser(@PathVariable("id") int id, @CookieValue(value = "SESSIONID") String cookie) throws HotelException {
        return userService.getUser(cookie, id);
    }

}
