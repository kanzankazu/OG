package com.gandsoft.openguide.API.APIrequest;

public class GetCheckDistanceLocationRequestModel {
    private String event_id;
    private String account_id;
    private String my_latitude;
    private String my_longtitude;
    private String dbver;

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getMy_latitude() {
        return my_latitude;
    }

    public void setMy_latitude(String my_latitude) {
        this.my_latitude = my_latitude;
    }

    public String getMy_longtitude() {
        return my_longtitude;
    }

    public void setMy_longtitude(String my_longtitude) {
        this.my_longtitude = my_longtitude;
    }

    public String getDbver() {
        return dbver;
    }

    public void setDbver(String dbver) {
        this.dbver = dbver;
    }
}
