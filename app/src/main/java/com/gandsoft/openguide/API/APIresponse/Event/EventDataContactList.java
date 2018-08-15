package com.gandsoft.openguide.API.APIresponse.Event;

import java.io.Serializable;

public class EventDataContactList implements Serializable {
    public String icon;
    public String icon_local;
    public String telephone;
    public String email;
    public String name;

    public String title;
    public int number;
    public String eventId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon_local() {
        return icon_local;
    }

    public void setIcon_local(String icon_local) {
        this.icon_local = icon_local;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
