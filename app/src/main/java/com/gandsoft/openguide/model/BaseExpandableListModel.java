package com.gandsoft.openguide.model;

import java.util.ArrayList;
import java.util.List;

public class BaseExpandableListModel {
    private String title;
    private String info;
    private List<SubBaseExpanderListModel> detail = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public List<SubBaseExpanderListModel> getDetail() {
        return detail;
    }

    public void setDetail(List<SubBaseExpanderListModel> detail) {
        this.detail = detail;
    }
}
