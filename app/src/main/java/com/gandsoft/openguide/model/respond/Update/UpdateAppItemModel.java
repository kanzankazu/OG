package com.gandsoft.openguide.model.respond.Update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateAppItemModel implements Serializable{
    private String applink;
    private String appversion;
    private String appupdatedt;

    public void consume(UpdateAppItemModel model){
        this.applink = model.getApplink();
        this.appversion = model.getAppversion();
        this.appupdatedt = model.getAppupdatedt();
    }

    public String getApplink() {
        return applink;
    }

    public void setApplink(String applink) {
        this.applink = applink;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }

    public String getAppupdatedt() {
        return appupdatedt;
    }

    public void setAppupdatedt(String appupdatedt) {
        this.appupdatedt = appupdatedt;
    }
}
