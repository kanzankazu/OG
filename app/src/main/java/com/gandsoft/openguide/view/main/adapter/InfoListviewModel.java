package com.gandsoft.openguide.view.main.adapter;

public class InfoListviewModel {
    String title;
    int picTitle;

    public InfoListviewModel(String title, int picTitle) {
        this.title = title;
        this.picTitle = picTitle;
    }

    public InfoListviewModel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPicTitle() {
        return picTitle;
    }

    public void setPicTitle(int picTitle) {
        this.picTitle = picTitle;
    }
}
