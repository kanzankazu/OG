package com.gandsoft.openguide.API;

import com.gandsoft.openguide.API.APIrequest.Event.EventDataRequestModel;
import com.gandsoft.openguide.API.APIrequest.Gallery.GalleryRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentCheckinRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCaptionDeleteRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCaptionSetRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCommentDeleteRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCommentGetRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCommentSetRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostImageCaptionRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostLikeRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentRequestModel;
import com.gandsoft.openguide.API.APIrequest.Login.LoginRequestModel;
import com.gandsoft.openguide.API.APIrequest.PostFeedbackTheEventRequestModel;
import com.gandsoft.openguide.API.APIrequest.UserData.UserDataRequestModel;
import com.gandsoft.openguide.API.APIrequest.UserUpdate.UserUpdateRequestModel;
import com.gandsoft.openguide.API.APIrequest.VerificationStatusLoginAppUserRequestModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.Gallery.GalleryResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostCaptionDeleteResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostCommentDeleteResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostCommentGetResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostCommentSetResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.API.APIresponse.LocalBaseResponseModel;
import com.gandsoft.openguide.API.APIresponse.Login.LoginResponseModel;
import com.gandsoft.openguide.API.APIresponse.PostFeedbackTheEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserUpdate.UserUpdateResponseModel;
import com.gandsoft.openguide.API.APIresponse.VerificationStatusLoginAppUserResponseModel;
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

    public static Call<List<GalleryResponseModel>> doGalleryRet(GalleryRequestModel requestModel) {
        return getAPIService().galleryDataRet(requestModel);
    }

    public static Call<List<HomeContentResponseModel>> doHomeContentDataRet(HomeContentRequestModel requestModel) {
        return getAPIService().homeContentDataRet(requestModel);
    }

    public static Call<List<LocalBaseResponseModel>> doHomeContentCheckinRet(HomeContentCheckinRequestModel requestModel) {
        return getAPIService().homeContentCheckinRet(requestModel);
    }

    public static Call<List<LocalBaseResponseModel>> doHomeContentPostCaptionRet(HomeContentPostCaptionSetRequestModel requestModel) {
        return getAPIService().homeContentPostCaptionRet(requestModel);
    }

    public static Call<List<LocalBaseResponseModel>> doHomeContentPostImageCaptionRet(HomeContentPostImageCaptionRequestModel requestModel) {
        return getAPIService().homeContentPostImageCaptionRet(requestModel);
    }

    public static Call<List<HomeContentPostCommentSetResponseModel>> doHomeContentPostCommentRet(HomeContentPostCommentSetRequestModel requestModel) {
        return getAPIService().homeContentPostCommentRet(requestModel);
    }

    public static Call<List<LocalBaseResponseModel>> doHomeContentPostLikeRet(HomeContentPostLikeRequestModel requestModel) {
        return getAPIService().homeContentPostLikeRet(requestModel);
    }

    public static Call<List<HomeContentPostCaptionDeleteResponseModel>> dohomeContentPostDeleteRet(HomeContentPostCaptionDeleteRequestModel requestModel) {
        return getAPIService().homeContentPostDeleteRet(requestModel);
    }

    public static Call<List<HomeContentPostCommentDeleteResponseModel>> dohomeContentPostCommentDeleteRet(HomeContentPostCommentDeleteRequestModel requestModel) {
        return getAPIService().homeContentPostCommentDeleteRet(requestModel);
    }

    public static Call<List<HomeContentPostCommentGetResponseModel>> dohomeContentPostCommentGetRet(HomeContentPostCommentGetRequestModel requestModel) {
        return getAPIService().homeContentPostCommentGetRet(requestModel);
    }

    public static Call<List<VerificationStatusLoginAppUserResponseModel>> doVerificationStatusLoginAppUserRet(VerificationStatusLoginAppUserRequestModel requestModel) {
        return getAPIService().verificationStatusLoginAppUserRet(requestModel);
    }

    public static Call<List<PostFeedbackTheEventResponseModel>> doPostFeedbackTheEventRet(PostFeedbackTheEventRequestModel requestModel) {
        return getAPIService().postFeedbackTheEventRet(requestModel);
    }
}
