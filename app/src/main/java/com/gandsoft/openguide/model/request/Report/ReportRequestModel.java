package com.gandsoft.openguide.model.request.Report;

public class ReportRequestModel {
    String name;
    String email;
    String phonenmbr;
    String website;
    String msg;
    String ip;
    String detaildevice;
    String token;
    String apppkg;
    String appversion;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenmbr() {
        return phonenmbr;
    }

    public void setPhonenmbr(String phonenmbr) {
        this.phonenmbr = phonenmbr;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDetaildevice() {
        return detaildevice;
    }

    public void setDetaildevice(String detaildevice) {
        this.detaildevice = detaildevice;
    }

    public String getApppkg() {
        return apppkg;
    }

    public void setApppkg(String apppkg) {
        this.apppkg = apppkg;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }
}
