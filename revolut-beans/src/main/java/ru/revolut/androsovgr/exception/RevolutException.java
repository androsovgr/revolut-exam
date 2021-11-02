package ru.revolut.androsovgr.exception;

public class RevolutException extends RuntimeException {
    public RevolutException(String message) {
        super(message);
    }

    public RevolutException(String message, Throwable cause) {
        super(message, cause);
    }
}
