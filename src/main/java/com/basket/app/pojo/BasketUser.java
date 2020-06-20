package com.basket.app.pojo;

import com.datastax.driver.core.LocalDate;
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
    private String uiPassword;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private String password;
    private String confirmpassword;
    private String teachingExperience;
    private String industrialExperience;
    private String institute;
    private String location;
    private boolean enabled;

    private String vertoken;

    public String getUiPassword() {
        return uiPassword;
    }

    public void setUiPassword(String uiPassword) {
        this.uiPassword = uiPassword;
    }

    public String getVertoken() {
        return vertoken;
    }

    public void setVertoken(String vertoken) {
        this.vertoken = vertoken;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    private LocalDate created;
    private LocalDate updated;


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

    @Override
    public String toString() {
        return "BasketUser{" +
                "name='" + name + '\'' +
                ", userType='" + userType + '\'' +
                ", category='" + category + '\'' +
                ", subject='" + subject + '\'' +
                ", emailId='" + emailId + '\'' +
                ", mobile='" + mobile + '\'' +
                ", freeTextRequirement='" + freeTextRequirement + '\'' +
                ", mode='" + mode + '\'' +
                ", uiPassword='" + uiPassword + '\'' +
                ", password='" + password + '\'' +
                ", confirmpassword='" + confirmpassword + '\'' +
                ", teachingExperience='" + teachingExperience + '\'' +
                ", industrialExperience='" + industrialExperience + '\'' +
                ", institute='" + institute + '\'' +
                ", location='" + location + '\'' +
                ", enabled=" + enabled +
                ", vertoken='" + vertoken + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
