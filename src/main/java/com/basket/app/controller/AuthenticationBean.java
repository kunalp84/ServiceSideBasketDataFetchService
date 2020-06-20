package com.basket.app.controller;

import lombok.Data;

public class AuthenticationBean {
    String message;
    String token;

    public String getMessage() {
        return message;
    }

    public AuthenticationBean(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AuthenticationBean(String message) {
        this.message = message;
    }
}
