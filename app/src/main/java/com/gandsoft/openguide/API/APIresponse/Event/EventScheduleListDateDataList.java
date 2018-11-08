package com.gandsoft.openguide.API.APIresponse.Event;

public class EventScheduleListDateDataList {
    public String id;
    public String waktu;
    public String schedule_title;
    public String location;
    public String detail;
    public String action;
    public String link;
    public String linkvote;
    public EventScheduleListExternalframe externalframe;
    public String status;

    public int number;
    public String eventId;
    public String date;
    private String groupCode;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getSchedule_title() {
        return schedule_title;
    }

    public void setSchedule_title(String schedule_title) {
        this.schedule_title = schedule_title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkvote() {
        return linkvote;
    }

    public void setLinkvote(String linkvote) {
        this.linkvote = linkvote;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public EventScheduleListExternalframe getExternalframe() {
        return externalframe;
    }

    public void setExternalframe(EventScheduleListExternalframe externalframe) {
        this.externalframe = externalframe;
    }
}
