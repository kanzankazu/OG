package com.gandsoft.openguide.API.APIrequest;

public class VerificationStatusLoginAppUserRequestModel {
    private String account_id;
    private String device_app;
    private String dbver;

    public VerificationStatusLoginAppUserRequestModel() {
    }

    public VerificationStatusLoginAppUserRequestModel(String account_id, String device_app, String dbver) {
        this.account_id = account_id;
        this.device_app = device_app;
        this.dbver = dbver;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getDevice_app() {
        return device_app;
    }

    public void setDevice_app(String device_app) {
        this.device_app = device_app;
    }

    public String getDbver() {
        return dbver;
    }

    public void setDbver(String dbver) {
        this.dbver = dbver;
    }
}
