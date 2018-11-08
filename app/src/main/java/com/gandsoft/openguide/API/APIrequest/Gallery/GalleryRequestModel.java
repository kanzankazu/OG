package com.gandsoft.openguide.API.APIrequest.Gallery;

public class GalleryRequestModel {
    public String phonenumber;
    public String dbver;
    public String id_event;
    public String kondisi;
    public String last_date;
    public String lastid;
    public String firstid;

    public GalleryRequestModel(String phonenumber, String dbver, String id_event) {
        this.phonenumber = phonenumber;
        this.dbver = dbver;
        this.id_event = id_event;
        this.kondisi = kondisi;
        this.lastid = lastid;
        this.firstid = firstid;
    }

    public GalleryRequestModel() {

    }

    public String getLast_date() {
        return last_date;
    }

    public void setLast_date(String last_date) {
        this.last_date = last_date;
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

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
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
