package com.gandsoft.openguide.API.APIresponse.HomeContent;

import android.os.Parcel;
import android.os.Parcelable;

public class HomeContentCommentModelParcelable implements Parcelable {
    public static final Creator CREATOR = new Creator() {
        public HomeContentCommentModelParcelable createFromParcel(Parcel in) {
            return new HomeContentCommentModelParcelable(in);
        }

        @Override
        public HomeContentCommentModelParcelable[] newArray(int i) {
            return new HomeContentCommentModelParcelable[i];
        }
    };
    public String id;
    public String like;
    public String account_id;
    public String total_comment;
    public int status_like;
    public String username;
    public String jabatan;
    public String date_created;
    public String image_icon;
    public String image_icon_local;
    public String image_posted;
    public String image_posted_local;
    public String keterangan;
    public String new_event;
    public int number;
    public String eventId;

    public HomeContentCommentModelParcelable() {
    }

    public HomeContentCommentModelParcelable(String id, String like, String account_id, String total_comment, int status_like, String image_icon, String username, String jabatan, String date_created, String image_posted, String keterangan, String new_event) {
        this.id = id;
        this.like = like;
        this.account_id = account_id;
        this.total_comment = total_comment;
        this.status_like = status_like;
        this.image_icon = image_icon;
        this.username = username;
        this.jabatan = jabatan;
        this.date_created = date_created;
        this.image_posted = image_posted;
        this.keterangan = keterangan;
        this.new_event = new_event;
    }

    public HomeContentCommentModelParcelable(Parcel in) {
        id = in.readString();
        like = in.readString();
        account_id = in.readString();
        total_comment = in.readString();
        status_like = in.readInt();
        username = in.readString();
        jabatan = in.readString();
        date_created = in.readString();
        image_icon = in.readString();
        image_icon_local = in.readString();
        image_posted = in.readString();
        image_posted_local = in.readString();
        keterangan = in.readString();
        new_event = in.readString();
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

    public String getImage_icon() {
        return image_icon;
    }

    public void setImage_icon(String image_icon) {
        this.image_icon = image_icon;
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

    public String getImage_posted() {
        return image_posted;
    }

    public void setImage_posted(String image_posted) {
        this.image_posted = image_posted;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNew_event() {
        return new_event;
    }

    public void setNew_event(String new_event) {
        this.new_event = new_event;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getImage_icon_local() {
        return image_icon_local;
    }

    public void setImage_icon_local(String image_icon_local) {
        this.image_icon_local = image_icon_local;
    }

    public String getImage_posted_local() {
        return image_posted_local;
    }

    public void setImage_posted_local(String image_posted_local) {
        this.image_posted_local = image_posted_local;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(like);
        parcel.writeString(account_id);
        parcel.writeString(total_comment);
        parcel.writeInt(status_like);
        parcel.writeString(username);
        parcel.writeString(jabatan);
        parcel.writeString(date_created);
        parcel.writeString(image_icon);
        parcel.writeString(image_icon_local);
        parcel.writeString(image_posted);
        parcel.writeString(image_posted_local);
        parcel.writeString(keterangan);
        parcel.writeString(new_event);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
