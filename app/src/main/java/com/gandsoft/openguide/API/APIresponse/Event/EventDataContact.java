package com.gandsoft.openguide.API.APIresponse.Event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventDataContact implements Serializable {
    public String title;
    public List<EventDataContactList> contact_list = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<EventDataContactList> getContact_list() {
        return contact_list;
    }

    public void setContact_list(List<EventDataContactList> contact_list) {
        this.contact_list = contact_list;
    }
}
