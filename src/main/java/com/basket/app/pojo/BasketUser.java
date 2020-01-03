package com.basket.app.pojo;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Table(name="basketuser")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasketUser {

    @PartitionKey
    private String name;
    @ClusteringColumn
    private String userType;
    private String category;
    private String subject;
    private String emailId;
    private String mobile;
    private String freeTextRequirement;
    private String mode;
    private String password;
    private String confirmpassword;
    private String teachingExperience;
    private String industrialExperience;
    private String institute;
    private String location;


    public String getTeachingExperience() {
        return teachingExperience;
    }

    public void setTeachingExperience(String teachingExperience) {
        this.teachingExperience = teachingExperience;
    }

    public String getIndustrialExperience() {
        return industrialExperience;
    }

    public void setIndustrialExperience(String industrialExperience) {
        this.industrialExperience = industrialExperience;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }





    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFreeTextRequirement() {
        return freeTextRequirement;
    }

    public void setFreeTextRequirement(String freeTextRequirement) {
        this.freeTextRequirement = freeTextRequirement;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
