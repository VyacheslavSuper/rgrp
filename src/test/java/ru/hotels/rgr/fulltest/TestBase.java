package ru.hotels.rgr.fulltest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Assert;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ru.hotels.rgr.dto.request.RequestBase;
import ru.hotels.rgr.dto.request.users.RegisterUserRequest;
import ru.hotels.rgr.dto.response.ErrorResponse;
import ru.hotels.rgr.dto.response.ResponseBase;
import ru.hotels.rgr.exception.HotelErrorCode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBase {
    protected RestTemplate restTemplate = new RestTemplate();

    @LocalServerPort
    private int port;

    protected String getRootUrl() {
        return "http://localhost:" + port;
    }

    //-------------------------------User----------------------------
    protected String registerUser(int status, String login, HotelErrorCode errorCode) {
        try {
            RegisterUserRequest registerUserRequest = new RegisterUserRequest(login, "TestName", "mytestemail@mail.ru", "MyGoodPasswordTest");
            HttpEntity<RegisterUserRequest> request = new HttpEntity<>(registerUserRequest);
            HttpEntity<ResponseBase> response = restTemplate.exchange(getRootUrl() + "/api/users", HttpMethod.POST, request, ResponseBase.class);
            HttpHeaders headers = response.getHeaders();
            String headerCookie = headers.getFirst(HttpHeaders.SET_COOKIE);
            Assert.assertNotNull(headerCookie);
            return headerCookie.substring(headerCookie.indexOf("=") + 1, headerCookie.indexOf(" ") - 1);
        } catch (HttpServerErrorException | HttpClientErrorException exc) {
            assertEquals(status, exc.getStatusCode().value());
            ErrorResponse er = new Gson().fromJson(exc.getResponseBodyAsString(),ErrorResponse.class);
            Assert.assertEquals(er.getErrorCode(), errorCode);
            return null;
        }
    }

    protected void deleteUser(int status, String cookie, HotelErrorCode errorCode) {
        try {
            HttpEntity request = new HttpEntity<>(new RequestBase(), getHeaders(cookie));
            HttpEntity<ResponseBase> response = restTemplate.exchange(getRootUrl() + "/api/users", HttpMethod.DELETE, request, ResponseBase.class);
            ResponseBase responseBase = response.getBody();
            Assert.assertNotNull(responseBase);
        } catch (HttpServerErrorException | HttpClientErrorException exc) {
            assertEquals(status, exc.getStatusCode().value());
            ErrorResponse er = new Gson().fromJson(exc.getResponseBodyAsString(),ErrorResponse.class);
            Assert.assertEquals(er.getErrorCode(), errorCode);
        }
    }


    protected void clear() {
        HttpEntity<ResponseBase> response = restTemplate.exchange(getRootUrl() + "/api/debug/clear", HttpMethod.POST, new HttpEntity<>(new RequestBase()), ResponseBase.class);
        Assert.assertTrue(response.hasBody());
    }

    ////////////////////////////////

    private HttpHeaders getHeaders(String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "SESSIONID=" + cookie);
        return headers;
    }


}
