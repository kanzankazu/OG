package com.gandsoft.openguide.API;

import com.gandsoft.openguide.model.request.Login.LoginRequestModel;
import com.gandsoft.openguide.model.request.Report.ReportRequestModel;
import com.gandsoft.openguide.model.request.Update.UpdateRequestModel;
import com.gandsoft.openguide.model.respond.BaseResponseModel;
import com.gandsoft.openguide.model.respond.Login.LoginResponseModel;
import com.gandsoft.openguide.model.respond.Oui.OuiResponseModel;
import com.gandsoft.openguide.model.respond.Report.ReportRespondModel;
import com.gandsoft.openguide.model.respond.Update.UpdateAppRespondModel;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by glenn on 1/25/18.
 */

public interface ApiServices {

    @POST("mail")
    Call<ReportRespondModel> Report(@Body ReportRequestModel model);

    @POST("appupdate")
    Call<UpdateAppRespondModel> Update(@Body UpdateRequestModel model);

    @GET("oui")
    Call<OuiResponseModel> Oui();

    @POST("verification_phonenumber_firebase")
    Call<ArrayList<LoginResponseModel>> Login(@Body LoginRequestModel model);

    @POST("verification_phonenumber_firebase")
    Call<LoginResponseModel> Login2(@Body LoginRequestModel model);

    @POST("verification_phonenumber_firebase")
    Call<ResponseBody> Login3(LoginRequestModel request);
}
