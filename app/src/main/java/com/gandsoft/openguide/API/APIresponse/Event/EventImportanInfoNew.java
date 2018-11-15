package com.gandsoft.openguide.API.APIresponse.Event;

import java.util.ArrayList;
import java.util.List;

public class EventImportanInfoNew {
    public String title;
    public String info;
    public List<EventImportanInfoNewDetail> detail = new ArrayList<>();

    public int number;
    public String eventId;

    public String title_image;
    public String name_image;
    public String name_image_local;
    public String detail_image;

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

    public List<EventImportanInfoNewDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<EventImportanInfoNewDetail> detail) {
        this.detail = detail;
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

    public String getName_image_local() {
        return name_image_local;
    }

    public void setName_image_local(String name_image_local) {
        this.name_image_local = name_image_local;
    }

    public String getDetail_image() {
        return detail_image;
    }

    public void setDetail_image(String detail_image) {
        this.detail_image = detail_image;
    }
}
