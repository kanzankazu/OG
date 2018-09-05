package com.gandsoft.openguide.API.APIrequest.Gallery;

public class GalleryRequestModel {
    public String phonenumber;
    public String dbver;
    public String id_event;

    public GalleryRequestModel( String phonenumber, String dbver, String id_event) {
        this.phonenumber = phonenumber;
        this.dbver = dbver;
        this.id_event = id_event;
    }

    public GalleryRequestModel() {

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

}
