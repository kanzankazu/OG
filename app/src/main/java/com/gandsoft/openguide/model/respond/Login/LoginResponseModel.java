package com.gandsoft.openguide.model.respond.Login;


import java.io.Serializable;

public class LoginResponseModel implements Serializable {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}