package ru.hotels.rgr.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.UUID;

@Service
public class CookieService {

    private String generateCookie() {
        return UUID.randomUUID().toString();
    }

    public Cookie createCookie() {
        Cookie cookie = new Cookie("SESSIONID", generateCookie());
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public Cookie deleteCookie() {
        Cookie cookie = new Cookie("SESSIONID", null);
        cookie.setMaxAge(0);
        return cookie;
    }
}
