package com.gandsoft.openguide.API;

import com.gandsoft.openguide.API.APIrequest.Event.EventDataRequestModel;
import com.gandsoft.openguide.API.APIrequest.Gallery.GalleryRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentRequestModel;
import com.gandsoft.openguide.API.APIrequest.Login.LoginRequestModel;
import com.gandsoft.openguide.API.APIrequest.UserData.UserDataRequestModel;
import com.gandsoft.openguide.API.APIrequest.UserUpdate.UserUpdateRequestModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.Gallery.GalleryResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.API.APIresponse.Login.LoginResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserUpdate.UserUpdateResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
    Call<List<LoginResponseModel>> loginRet(@Body LoginRequestModel model);

    @POST("get_list_user_event")
    Call<List<UserDataResponseModel>> userDataRet(@Body UserDataRequestModel model);

    @POST("save_profile_account_user")
    Call<List<UserUpdateResponseModel>> userUpdateRet(@Body UserUpdateRequestModel model);

    @POST("get_data_event")
    Call<List<EventDataResponseModel>> eventDataRet(@Body EventDataRequestModel model);

    @POST("get_new_gallery_content")
    Call<List<GalleryResponseModel>> galleryDataRet(@Body GalleryRequestModel model);

    @POST("get_new_home_content")
    Call<List<HomeContentResponseModel>> homeContentDataRet(@Body HomeContentRequestModel model);


}
