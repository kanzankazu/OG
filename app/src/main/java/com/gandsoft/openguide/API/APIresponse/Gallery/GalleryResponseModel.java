package com.gandsoft.openguide.API.APIresponse.Gallery;

public class GalleryResponseModel {
   private String id;
   private String like;
   private String account_id;
   private String total_comment;
   private String status_like;
   private String username;
   private String caption;
   private String image_posted;
   private String image_icon;
   private String image_postedLocal;
   private String image_iconLocal;

   private String event_id;
   private int number;

    public GalleryResponseModel(String id, String event_id, String like, String account_id, String total_comment, String status_like, String username, String caption, String image_posted, String image_icon, int number) {
        this.id = id;
        this.event_id = event_id;
        this.like = like;
        this.account_id = account_id;
        this.total_comment = total_comment;
        this.status_like = status_like;
        this.username = username;
        this.caption = caption;
        this.image_posted = image_posted;
        this.image_icon = image_icon;
        this.image_postedLocal = image_postedLocal;
        this.image_iconLocal = image_iconLocal;
        this.number = number;
    }

    public GalleryResponseModel(){}

    public String getImage_postedLocal() {
        return image_postedLocal;
    }

    public void setImage_postedLocal(String image_postedLocal) {
        this.image_postedLocal = image_postedLocal;
    }

    public String getImage_iconLocal() {
        return image_iconLocal;
    }

    public void setImage_iconLocal(String image_iconLocal) {
        this.image_iconLocal = image_iconLocal;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getImage_icon() {
        return image_icon;
    }

    public void setImage_icon(String image_icon) {
        this.image_icon = image_icon;
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

    public String getStatus_like() {
        return status_like;
    }

    public void setStatus_like(String status_like) {
        this.status_like = status_like;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage_posted() {
        return image_posted;
    }

    public void setImage_posted(String image_posted) {
        this.image_posted = image_posted;
    }
}
