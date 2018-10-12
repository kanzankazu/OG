package com.gandsoft.openguide.API.APIrequest.UserData;

public class GetListUserEventRequestModel {
    private String accountid;
    private int version_data;
    private int dbver;

    public GetListUserEventRequestModel(String accountid, int db_version, int version_data) {
        this.accountid = accountid;
        this.dbver = db_version;
        this.version_data = version_data;
    }

    public GetListUserEventRequestModel() {

    }

    public int getVersion_data() {
        return version_data;
    }

    public void setVersion_data(int version_data) {
        this.version_data = version_data;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public int getDbver() {
        return dbver;
    }

    public void setDbver(int dbver) {
        this.dbver = dbver;
    }
}
