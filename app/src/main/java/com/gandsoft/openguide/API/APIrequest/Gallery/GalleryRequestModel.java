package com.gandsoft.openguide.API.APIrequest.Gallery;

public class GalleryRequestModel {
    String phone_number,id_event,dbver,kondisi,last_date,lastid,firstid;

    public GalleryRequestModel(String phone_number, String id_event, String dbver, String kondisi, String last_date, String lastid, String firstid) {
        this.phone_number = phone_number;
        this.id_event = id_event;
        this.dbver = dbver;
        this.kondisi = kondisi;
        this.last_date = last_date;
        this.lastid = lastid;
        this.firstid = firstid;
    }

    public GalleryRequestModel() {

    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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
