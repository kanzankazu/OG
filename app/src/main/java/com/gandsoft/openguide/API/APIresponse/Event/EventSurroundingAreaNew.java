package com.gandsoft.openguide.API.APIresponse.Event;

import java.util.ArrayList;
import java.util.List;

public class EventSurroundingAreaNew {
    public String title;
    public List<EventSurroundingAreaNewDetail> detail = new ArrayList<>();
    private int number;
    private String eventId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<EventSurroundingAreaNewDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<EventSurroundingAreaNewDetail> detail) {
        this.detail = detail;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getNumber() {
        return number;
    }

    public String getEventId() {
        return eventId;
    }
}
