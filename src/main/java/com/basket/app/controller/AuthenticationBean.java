package com.basket.app.controller;

public class AuthenticationBean {
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AuthenticationBean(String message) {
        this.message = message;
    }
}
