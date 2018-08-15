package com.gandsoft.openguide.API.APIrequest.Event;

public class EventDataRequestModel {
    public int version_data;
    public String phonenumber;
    public String dbver;
    public String id_event;
    public String pass;

    public int getVersion_data() {
        return version_data;
    }

    public void setVersion_data(int version_data) {
        this.version_data = version_data;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getDbver() {
        return dbver;
    }

    public void setDbver(String dbver) {
        this.dbver = dbver;
    }

    public String getId_event() {
        return id_event;
    }

    public void setId_event(String id_event) {
        this.id_event = id_event;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
