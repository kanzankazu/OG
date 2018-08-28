package com.gandsoft.openguide.API.APIresponse.Event;

import java.io.Serializable;

public class EventPlaceList  {

    public String latitude;
    public String longitude;
    public String title;
    public String icon;

    public int number;
    public String eventId;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    /**/

    /*List<EventPlaceListData> placeListDataList;

    public List<EventPlaceListData> getPlaceListDataList() {
        return placeListDataList;
    }

    public void setPlaceListDataList(List<EventPlaceListData> placeListDataList) {
        this.placeListDataList = placeListDataList;
    }*/
}
