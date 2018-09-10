package com.gandsoft.openguide.API.APIrequest.HomeContent;

public class HomeContentPostLikeRequestModel {
    String account_id,event_id,kondisi,id_content, val_like,status_like,dbver;

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public String getId_content() {
        return id_content;
    }

    public void setId_content(String id_content) {
        this.id_content = id_content;
    }

    public String getVal_like() {
        return val_like;
    }

    public void setVal_like(String val_like) {
        this.val_like = val_like;
    }

    public String getStatus_like() {
        return status_like;
    }

    public void setStatus_like(String status_like) {
        this.status_like = status_like;
    }

    public String getDbver() {
        return dbver;
    }

    public void setDbver(String dbver) {
        this.dbver = dbver;
    }
}
