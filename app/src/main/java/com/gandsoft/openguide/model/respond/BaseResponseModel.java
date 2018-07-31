package com.gandsoft.openguide.model.respond;

import java.io.Serializable;

import app.beelabs.com.codebase.base.response.BaseResponse;

/**
 * Created by glenn on 1/25/18.
 */

public class BaseResponseModel extends BaseResponse implements Serializable {

    private String message;
    private String ip;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
