package com.gandsoft.openguide.API.APIresponse.Event;

import java.io.Serializable;

public class EventImportanInfo implements Serializable {
    public String title;
    public String info;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
