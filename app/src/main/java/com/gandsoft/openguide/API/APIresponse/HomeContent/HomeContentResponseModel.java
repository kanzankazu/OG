package com.gandsoft.openguide.API.APIresponse.HomeContent;

public class HomeContentResponseModel {
    private String id;
    private String like;
    private String account_id;
    private String total_comment;
    private int status_like;
    private String username;
    private String image_icon;
    private String image_icon_local;
    private String image_posted;
    private String image_posted_local;
    private String caption;

    private String keterangan;
    private String date_created;
    private String jabatan;

    public int number;
    public String event_Id;

    public String new_event;
    public boolean is_new;

    public HomeContentResponseModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getTotal_comment() {
        return total_comment;
    }

    public void setTotal_comment(String total_comment) {
        this.total_comment = total_comment;
    }

    public int getStatus_like() {
        return status_like;
    }

    public void setStatus_like(int status_like) {
        this.status_like = status_like;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getImage_icon() {
        return image_icon;
    }

    public void setImage_icon(String image_icon) {
        this.image_icon = image_icon;
    }

    public String getImage_icon_local() {
        return image_icon_local;
    }

    public void setImage_icon_local(String image_icon_local) {
        this.image_icon_local = image_icon_local;
    }

    public String getImage_posted() {
        return image_posted;
    }

    public void setImage_posted(String image_posted) {
        this.image_posted = image_posted;
    }

    public String getImage_posted_local() {
        return image_posted_local;
    }

    public void setImage_posted_local(String image_posted_local) {
        this.image_posted_local = image_posted_local;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getEvent_Id() {
        return event_Id;
    }

    public void setEvent_Id(String event_Id) {
        this.event_Id = event_Id;
    }

    public String getNew_event() {
        return new_event;
    }

    public void setNew_event(String new_event) {
        this.new_event = new_event;
    }

    public boolean getIs_new() {
        return is_new;
    }

    public void setIs_new(boolean is_new) {
        this.is_new = is_new;
    }
}
