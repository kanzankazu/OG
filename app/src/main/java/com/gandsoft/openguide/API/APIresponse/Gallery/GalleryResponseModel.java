package com.gandsoft.openguide.API.APIresponse.Gallery;

public class GalleryResponseModel {
    String id, like, account_id,total_comment,status_like,username,caption,image_posted,image_icon;
    public int number;
    public String status;
    public String version_data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion_data() {
        return version_data;
    }

    public void setVersion_data(String version_data) {
        this.version_data = version_data;
    }

    public String getImage_icon() {
        return image_icon;
    }

    public void setImage_icon(String image_icon) {
        this.image_icon = image_icon;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
