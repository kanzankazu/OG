package com.gandsoft.openguide.API.APIresponse.Event;

public class EventScheduleListExternalframe {
    public String name;
    public String link;
    public String external;

    public EventScheduleListExternalframe() {
    }

    public EventScheduleListExternalframe(String name, String link, String external) {
        this.name = name;
        this.link = link;
        this.external = external;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getExternal() {
        return external;
    }

    public void setExternal(String external) {
        this.external = external;
    }
}
