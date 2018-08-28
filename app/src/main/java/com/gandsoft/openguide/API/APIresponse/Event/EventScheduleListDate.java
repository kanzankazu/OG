package com.gandsoft.openguide.API.APIresponse.Event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventScheduleListDate  {
    public String date;
    public List<EventScheduleListDateDataList> data = new ArrayList<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<EventScheduleListDateDataList> getData() {
        return data;
    }

    public void setData(List<EventScheduleListDateDataList> data) {
        this.data = data;
    }
}
