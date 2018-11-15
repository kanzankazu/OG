package com.gandsoft.openguide.API.APIresponse.Event;

public class EventImportanInfoNewDetail {
    public String title_image;
    public String name_image;
    public String name_image_local;
    public String detail_image;
    private int number;
    private String eventId;

    public String getName_image_local() {
        return name_image_local;
    }

    public void setName_image_local(String name_image_local) {
        this.name_image_local = name_image_local;
    }

    public String getTitle_image() {
        return title_image;
    }

    public void setTitle_image(String title_image) {
        this.title_image = title_image;
    }

    public String getName_image() {
        return name_image;
    }

    public void setName_image(String name_image) {
        this.name_image = name_image;
    }

    public String getDetail_image() {
        return detail_image;
    }

    public void setDetail_image(String detail_image) {
        this.detail_image = detail_image;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
