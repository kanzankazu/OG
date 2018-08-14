package com.gandsoft.openguide.API.APIresponse.UserUpdate;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserUpdateResponseModel implements Serializable {
    String status;
    String version_data;
    String image_url;

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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}