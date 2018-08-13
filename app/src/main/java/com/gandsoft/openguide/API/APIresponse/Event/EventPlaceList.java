package com.gandsoft.openguide.API.APIresponse.Event;

import java.io.Serializable;
import java.util.List;

public class EventPlaceList implements Serializable {
    List<EventPlaceListData> placeListDataList;

    public List<EventPlaceListData> getPlaceListDataList() {
        return placeListDataList;
    }

    public void setPlaceListDataList(List<EventPlaceListData> placeListDataList) {
        this.placeListDataList = placeListDataList;
    }
}
