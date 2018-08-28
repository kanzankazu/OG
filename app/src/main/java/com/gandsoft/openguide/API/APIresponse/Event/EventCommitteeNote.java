package com.gandsoft.openguide.API.APIresponse.Event;

public class EventCommitteeNote {
    public String id;
    public String icon;
    public String title;
    public String note;
    public String tanggal;
    public String has_been_opened;
    public String sort_inbox;

    public int number;
    public String eventId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getHas_been_opened() {
        return has_been_opened;
    }

    public void setHas_been_opened(String has_been_opened) {
        this.has_been_opened = has_been_opened;
    }

    public String getSort_inbox() {
        return sort_inbox;
    }

    public void setSort_inbox(String sort_inbox) {
        this.sort_inbox = sort_inbox;
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
}
