package com.gandsoft.openguide.model.request.Login;

public class LoginRequestModel {
    private String phonenumber;
    private String login_status;
    private String dbver;
    private String device_app;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getLogin_status() {
        return login_status;
    }

    public void setLogin_status(String login_status) {
        this.login_status = login_status;
    }

    public String getDbver() {
        return dbver;
    }

    public void setDbver(String dbver) {
        this.dbver = dbver;
    }

    public String getDevice_app() {
        return device_app;
    }

    public void setDevice_app(String device_app) {
        this.device_app = device_app;
    }
}
