package com.gandsoft.openguide.API.APIresponse.Event;

import java.io.Serializable;

public class EventAbout  {
    public String background;
    public String logo;
    public String description;

    public int number;
    public String eventId;

    public void setNumber(int number) {
        this.number = number;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
