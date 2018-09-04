package com.gandsoft.openguide.API.APIrequest.Gallery;

public class GalleryRequestModel {
    public int version_data;
    public String phonenumber;
    public String dbver;
    public String id_event;
    public String pass;

    public GalleryRequestModel(int version_data, String phonenumber, String dbver, String id_event, String pass) {
        this.version_data = version_data;
        this.phonenumber = phonenumber;
        this.dbver = dbver;
        this.id_event = id_event;
        this.pass = pass;
    }

    public GalleryRequestModel() {

    }

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
