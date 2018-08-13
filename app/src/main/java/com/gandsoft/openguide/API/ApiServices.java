package com.gandsoft.openguide.API;

import com.gandsoft.openguide.API.APIrequest.Login.LoginRequestModel;
import com.gandsoft.openguide.API.APIrespond.Login.LoginResponseModel;
import com.gandsoft.openguide.API.APIrespond.UserData.UserDataResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by glenn on 1/25/18.
 */

public interface ApiServices {

    /*@POST("mail")
    Call<ReportRespondModel> Report(@Body ReportRequestModel model);

    @POST("appupdate")
    Call<UpdateAppRespondModel> Update(@Body UpdateRequestModel model);

    @GET("oui")
    Call<OuiResponseModel> Oui();

    @POST("verification_phonenumber_firebase")
    Call<List<LoginResponseModel>> Login(@Body LoginRequestModel model);*/

    /**/
    @POST("verification_phonenumber_firebase")
    Call<List<LoginResponseModel>> LoginRet(@Body LoginRequestModel model);

    @POST("get_list_user_event")
    Call<List<UserDataResponseModel>> UserEventRet(@Body UserEventRequestModel model);
}
