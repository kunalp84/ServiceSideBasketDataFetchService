package com.basket.app.pojo;

import lombok.Data;

import java.util.Date;
@Data
public class BasketResponse<T> {
    private int sizeOfresponse;
    private Date timeOfResponse;
    private T items;
}
