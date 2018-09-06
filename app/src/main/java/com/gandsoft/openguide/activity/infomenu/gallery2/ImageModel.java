package com.gandsoft.openguide.activity.infomenu.gallery2;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageModel implements Parcelable {

    String name;
    String caption;
    String like;
    String statlike;
    String totcom;
    String url;

    public ImageModel() {
    }

    protected ImageModel(Parcel in) {
        name = in.readString();
        url = in.readString();
        caption = in.readString();
        like = in.readString();
        statlike = in.readString();
        totcom = in.readString();
    }

    public static final Creator<ImageModel> CREATOR = new Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel in) {
            return new ImageModel(in);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getStatlike() {
        return statlike;
    }

    public void setStatlike(String statlike) {
        this.statlike = statlike;
    }

    public String getTotcom() {
        return totcom;
    }

    public void setTotcom(String totcom) {
        this.totcom = totcom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(caption);
        dest.writeString(like);
        dest.writeString(statlike);
        dest.writeString(totcom);
    }
}
