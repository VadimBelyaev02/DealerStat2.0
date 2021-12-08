package com.leverx.dealerstat.exception;

public class NotValidException extends RuntimeException {

    public NotValidException(String message) {
        super(message);
    }

    public NotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
