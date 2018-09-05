package com.gandsoft.openguide.API.APIrequest.HomeContent;

public class HomeContentRequestModel {
    public String phonenumber;
    public String id_event;
    public String dbver;
    public String kondisi;
    public String last_date;
    public String lastid;
    public String firstid;

    public HomeContentRequestModel() {
    }

    public HomeContentRequestModel(String phonenumber, String id_event, String dbver, String kondisi, String last_date, String lastid, String firstid) {
        this.phonenumber = phonenumber;
        this.id_event = id_event;
        this.dbver = dbver;
        this.kondisi = kondisi;
        this.last_date = last_date;
        this.lastid = lastid;
        this.firstid = firstid;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getId_event() {
        return id_event;
    }

    public void setId_event(String id_event) {
        this.id_event = id_event;
    }

    public String getDbver() {
        return dbver;
    }

    public void setDbver(String dbver) {
        this.dbver = dbver;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public String getLast_date() {
        return last_date;
    }

    public void setLast_date(String last_date) {
        this.last_date = last_date;
    }

    public String getLastid() {
        return lastid;
    }

    public void setLastid(String lastid) {
        this.lastid = lastid;
    }

    public String getFirstid() {
        return firstid;
    }

    public void setFirstid(String firstid) {
        this.firstid = firstid;
    }
}
