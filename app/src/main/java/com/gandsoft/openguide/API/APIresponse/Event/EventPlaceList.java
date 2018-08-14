package com.gandsoft.openguide.API.APIresponse.Event;

import java.io.Serializable;
import java.util.List;

public class EventPlaceList implements Serializable {

    public String latitude;
    public String longitude;
    public String title;
    public String icon;
    public String number;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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
