package com.basket.app.pojo;

import lombok.Data;

import java.util.Date;
@Data
public class BasketResponse<T> {
    private int sizeOfresponse;
    private Date timeOfResponse;
    private String errorMessage;
    private Status status;
    private T items;
}
