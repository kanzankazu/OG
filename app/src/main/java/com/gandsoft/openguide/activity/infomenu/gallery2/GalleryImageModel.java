package com.gandsoft.openguide.activity.infomenu.gallery2;

import android.os.Parcel;
import android.os.Parcelable;

public class GalleryImageModel implements Parcelable {

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

    public GalleryImageModel() {
    }

    protected GalleryImageModel(Parcel in) {
        id = in.readString();
        like = in.readString();
        account_id = in.readString();
        total_comment = in.readString();
        status_like = in.readString();
        username = in.readString();
        caption = in.readString();
        image_posted = in.readString();
        image_icon = in.readString();
        image_postedLocal = in.readString();
        image_iconLocal = in.readString();
        event_id = in.readString();
        number = in.readInt();
    }

    public static final Creator<GalleryImageModel> CREATOR = new Creator<GalleryImageModel>() {
        @Override
        public GalleryImageModel createFromParcel(Parcel in) {
            return new GalleryImageModel(in);
        }

        @Override
        public GalleryImageModel[] newArray(int size) {
            return new GalleryImageModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(like);
        parcel.writeString(account_id);
        parcel.writeString(total_comment);
        parcel.writeString(status_like);
        parcel.writeString(username);
        parcel.writeString(caption);
        parcel.writeString(image_posted);
        parcel.writeString(image_icon);
        parcel.writeString(image_postedLocal);
        parcel.writeString(image_iconLocal);
        parcel.writeString(event_id);
        parcel.writeInt(number);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public void setTotal_comment(String total_comment) {
        this.total_comment = total_comment;
    }

    public void setStatus_like(String status_like) {
        this.status_like = status_like;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setImage_posted(String image_posted) {
        this.image_posted = image_posted;
    }

    public void setImage_icon(String image_icon) {
        this.image_icon = image_icon;
    }

    public void setImage_postedLocal(String image_postedLocal) {
        this.image_postedLocal = image_postedLocal;
    }

    public void setImage_iconLocal(String image_iconLocal) {
        this.image_iconLocal = image_iconLocal;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public String getLike() {
        return like;
    }

    public String getAccount_id() {
        return account_id;
    }

    public String getTotal_comment() {
        return total_comment;
    }

    public String getStatus_like() {
        return status_like;
    }

    public String getUsername() {
        return username;
    }

    public String getCaption() {
        return caption;
    }

    public String getImage_posted() {
        return image_posted;
    }

    public String getImage_icon() {
        return image_icon;
    }

    public String getImage_postedLocal() {
        return image_postedLocal;
    }

    public String getImage_iconLocal() {
        return image_iconLocal;
    }

    public String getEvent_id() {
        return event_id;
    }

    public int getNumber() {
        return number;
    }
}
