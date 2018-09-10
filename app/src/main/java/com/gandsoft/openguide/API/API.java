package com.gandsoft.openguide.API;

import com.gandsoft.openguide.API.APIrequest.Event.EventDataRequestModel;
import com.gandsoft.openguide.API.APIrequest.Gallery.GalleryRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentCheckinRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCaptionRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCommentRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostImageCaptionRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostLikeRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentRequestModel;
import com.gandsoft.openguide.API.APIrequest.Login.LoginRequestModel;
import com.gandsoft.openguide.API.APIrequest.UserData.UserDataRequestModel;
import com.gandsoft.openguide.API.APIrequest.UserUpdate.UserUpdateRequestModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.Gallery.GalleryResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostCaptionResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostCommentResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostImageCaptionResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.API.APIresponse.LocalBaseResponseModel;
import com.gandsoft.openguide.API.APIresponse.Login.LoginResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserUpdate.UserUpdateResponseModel;
import com.gandsoft.openguide.IConfig;

import java.util.List;

import retrofit2.Call;

public class API {

    /**/
    private static ApiServices getAPIService() {
        return RetrofitClient.getClient(IConfig.API_BASE_URL).create(ApiServices.class);
    }

    public static Call<List<LoginResponseModel>> doLoginRet(LoginRequestModel requestModel) {
        return getAPIService().loginRet(requestModel);
    }

    public static Call<List<UserDataResponseModel>> doUserDataRet(UserDataRequestModel requestModel) {
        return getAPIService().userDataRet(requestModel);
    }

    public static Call<List<UserUpdateResponseModel>> doUserUpdateRet(UserUpdateRequestModel requestModel) {
        return getAPIService().userUpdateRet(requestModel);
    }

    public static Call<List<EventDataResponseModel>> doEventDataRet(EventDataRequestModel requestModel) {
        return getAPIService().eventDataRet(requestModel);
    }

    public static Call<List<GalleryResponseModel>> doGalleryRet(GalleryRequestModel requestModel){
        return getAPIService().galleryDataRet(requestModel);
    }

    public static Call<List<HomeContentResponseModel>> doHomeContentDataRet(HomeContentRequestModel requestModel){
        return getAPIService().homeContentDataRet(requestModel);
    }

    public static Call<List<LocalBaseResponseModel>> doHomeContentCheckinRet(HomeContentCheckinRequestModel requestModel) {
        return getAPIService().homeContentCheckinRet(requestModel);
    }

    public static Call<List<LocalBaseResponseModel>> doHomeContentPostCaptionRet(HomeContentPostCaptionRequestModel requestModel) {
        return getAPIService().homeContentPostCaptionRet(requestModel);
    }

    public static Call<List<LocalBaseResponseModel>> doHomeContentPostImageCaptionRet(HomeContentPostImageCaptionRequestModel requestModel) {
        return getAPIService().homeContentPostImageCaptionRet(requestModel);
    }

    public static Call<List<LocalBaseResponseModel>> doHomeContentPostCommentRet(HomeContentPostCommentRequestModel requestModel) {
        return getAPIService().homeContentPostCommentRet(requestModel);
    }

    public static Call<List<LocalBaseResponseModel>> doHomeContentPostLikeRet(HomeContentPostLikeRequestModel requestModel) {
        return getAPIService().homeContentPostLikeRet(requestModel);
    }


}
