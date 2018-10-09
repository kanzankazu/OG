package com.gandsoft.openguide.API.APIresponse;

public class VerificationStatusLoginAppUserResponseModel {

    public String country_code;
    public String mobile_no;
    public String status_login;
    public String device;

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getStatus_login() {
        return status_login;
    }

    public void setStatus_login(String status_login) {
        this.status_login = status_login;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
