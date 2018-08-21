package com.gandsoft.openguide.API.APIresponse;

import com.gandsoft.openguide.API.APIresponse.Login.LoginResponseModel;

import java.io.Serializable;
import java.util.ArrayList;

import app.beelabs.com.codebase.base.response.BaseResponse;

/**
 * Created by glenn on 1/25/18.
 */

public class BaseResponseModel extends BaseResponse implements Serializable {

    private String message;
    private ArrayList<LoginResponseModel> loginModel = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<LoginResponseModel> getLoginModel() {
        return loginModel;
    }

    public void setLoginModel(ArrayList<LoginResponseModel> loginModel) {
        this.loginModel = loginModel;
    }
}
