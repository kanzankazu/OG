package com.gandsoft.openguide.API.APIrequest;

public class PostVerifyLoginUserRequestModel {
    private String phonenumber;     //
    private String token;           //- kosongin/diisi value sms dari firebase
    private String password;        //
    private String kondisi;         //(klo new user -> createpass else oldpassword)
    private String dbver;           //

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public String getDbver() {
        return dbver;
    }

    public void setDbver(String dbver) {
        this.dbver = dbver;
    }
}
