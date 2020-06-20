package com.basket.app.pojo;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.mapping.annotations.Table;

@Table(name="otpuser")
public class OtpStorage {

   private String name;
    private String otpKey;
    private LocalDate created;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtpKey() {
        return otpKey;
    }

    public void setOtpKey(String otpKey) {
        this.otpKey = otpKey;
    }

    @Override
    public String toString() {
        return "OtpStorage{" +
                "name='" + name + '\'' +
                ", otpKey='" + otpKey + '\'' +
                ", created=" + created +
                '}';
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }
}
