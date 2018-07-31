package com.gandsoft.openguide.model.request.Update;

public class UpdateRequestModel {
    private String apppkg;
    private String appname;
    private String appversion;

    public UpdateRequestModel(String apppkg, String appname, String appversion) {
        this.apppkg = apppkg;
        this.appname = appname;
        this.appversion = appversion;
    }

    public String getApppkg() {
        return apppkg;
    }

    public void setApppkg(String apppkg) {
        this.apppkg = apppkg;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }


}
