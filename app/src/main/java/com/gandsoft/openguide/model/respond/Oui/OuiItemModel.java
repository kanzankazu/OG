package com.gandsoft.openguide.model.respond.Oui;

import java.io.Serializable;

public class OuiItemModel implements Serializable {
    private String mac;
    private String name;

    public void consume(OuiItemModel model) {
        this.mac = model.getMac();
        this.name = model.getName();
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
