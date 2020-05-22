package ru.hotels.rgr.exception;

public enum HotelErrorCode {
    UNKNOWN_ERROR("Unknown error"),
    DATABASE_ERROR("Internal Server Error"),

    BAD_REQUEST("Bad request"),

    INTERNAL_SERVER_ERROR("Internal server error"),
    WRONG_DEBUG_MAPPER("Internal Server Error"),

    RESERVATION_NOT_AVAILABLE("Not available"),

    NOT_ENOUGH_RIGHTS("You are not a ADMIN"),
    ALREADY_ADMIN("This user is already ADMIN"),

    NO_COOKIE("You do not have cookies"),

    PRIORITY_MATCHES("The old priority is the same as the new one"),

    USER_DELETED("User is deleted"),

    ROOM_NOT_FOUND(""),

    RESERVATION_NOT_FOUND(""),

    USER_MATCHES("User matches"),
    USER_AUTHORIZED("User is already authorized"),
    USER_UNAUTHORIZED("User not yet authorized"),
    USER_ID_NOT_FOUND("User with id %s not found"),
    LOGIN_NOT_FOUND("User with this login %s not found"),
    LOGIN_ALREADY_EXISTS("User %s already exists"),
    LOGIN_OR_PASSWORD_INVALID("login or password invalid");

    private String message;

    HotelErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HotelErrorCode{" +
                "message='" + message + '\'' +
                '}';
    }
}
