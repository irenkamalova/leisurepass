package com.kamalova.leisurepass.utils;

public enum Messages {
    PASS_ACTIVE("Pass is active"),
    PASS_EXPIRED("Pass is expired");

    private final String message;
    Messages(String msg) {
        message = msg;
    }

    public String getMessage() {
        return message;
    }
}
