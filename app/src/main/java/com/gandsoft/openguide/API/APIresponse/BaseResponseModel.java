package com.gandsoft.openguide.API.APIresponse;

import com.gandsoft.openguide.API.APIresponse.Login.PostVerifyPhonenumberFirebaseResponseModel;

import java.util.ArrayList;

import app.beelabs.com.codebase.base.response.BaseResponse;

/**
 * Created by glenn on 1/25/18.
 */

public class BaseResponseModel extends BaseResponse {

    private String message;
    private ArrayList<PostVerifyPhonenumberFirebaseResponseModel> loginModel = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<PostVerifyPhonenumberFirebaseResponseModel> getLoginModel() {
        return loginModel;
    }

    public void setLoginModel(ArrayList<PostVerifyPhonenumberFirebaseResponseModel> loginModel) {
        this.loginModel = loginModel;
    }
}
