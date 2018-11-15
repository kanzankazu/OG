package com.gandsoft.openguide.API;

import com.gandsoft.openguide.API.APIrequest.Event.EventDataRequestModel;
import com.gandsoft.openguide.API.APIrequest.Gallery.GalleryRequestModel;
import com.gandsoft.openguide.API.APIrequest.GetCheckDistanceLocationRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentCheckinRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCaptionDeleteRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCaptionSetRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCommentDeleteRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCommentGetRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCommentSetRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostImageCaptionRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostLikeRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentRequestModel;
import com.gandsoft.openguide.API.APIrequest.Login.PostVerifyPhonenumberFirebaseRequestModel;
import com.gandsoft.openguide.API.APIrequest.PostFeedbackTheEventRequestModel;
import com.gandsoft.openguide.API.APIrequest.PostVerifyLoginUserRequestModel;
import com.gandsoft.openguide.API.APIrequest.PostVerifyTokenFirebaseRequestModel;
import com.gandsoft.openguide.API.APIrequest.UserData.GetListUserEventRequestModel;
import com.gandsoft.openguide.API.APIrequest.UserUpdate.UserUpdateRequestModel;
import com.gandsoft.openguide.API.APIrequest.VerificationStatusLoginAppUserRequestModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.GetCheckDistanceLocationResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostCaptionDeleteResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostCommentDeleteResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostCommentGetResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostCommentSetResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.API.APIresponse.LocalBaseResponseModel;
import com.gandsoft.openguide.API.APIresponse.Login.PostVerifyPhonenumberFirebaseResponseModel;
import com.gandsoft.openguide.API.APIresponse.PostFeedbackTheEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.PostVerifyLoginUserResponseModel;
import com.gandsoft.openguide.API.APIresponse.PostVerifyTokenFirebaseResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.GetListUserEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserUpdate.UserUpdateResponseModel;
import com.gandsoft.openguide.API.APIresponse.VerificationStatusLoginAppUserResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiServices {

    /**/
    @POST("verification_phonenumber_firebase")
    Call<List<PostVerifyPhonenumberFirebaseResponseModel>> postVerifyPhonenumberFirebaseRet(@Body PostVerifyPhonenumberFirebaseRequestModel model);

    @POST("verification_token_account_firebase")
    Call<List<PostVerifyTokenFirebaseResponseModel>> postVerivyTokenAccountFirebaseRet(@Body PostVerifyTokenFirebaseRequestModel model);

    @POST("verification_login_users")
    Call<List<PostVerifyLoginUserResponseModel>> postVerificationLoginUsersRet(@Body PostVerifyLoginUserRequestModel model);

    @POST("get_list_user_event")
    Call<List<GetListUserEventResponseModel>> getListUserEventRet(@Body GetListUserEventRequestModel model);

    @POST("save_profile_account_user")
    Call<List<UserUpdateResponseModel>> userUpdateRet(@Body UserUpdateRequestModel model);

    @POST("get_data_event")
    Call<List<EventDataResponseModel>> eventDataRet(@Body EventDataRequestModel model);

    @POST("get_new_gallery_content")
    Call<List<HomeContentResponseModel>> galleryDataRet(@Body GalleryRequestModel model);

    @POST("get_new_home_content")
    Call<List<HomeContentResponseModel>> homeContentDataRet(@Body HomeContentRequestModel model);

    @POST("checkin_event_verification_status")
    Call<List<LocalBaseResponseModel>> homeContentCheckinRet(@Body HomeContentCheckinRequestModel model);

    @POST("post_caption_home_content")
    Call<List<LocalBaseResponseModel>> homeContentPostCaptionRet(@Body HomeContentPostCaptionSetRequestModel model);

    @POST("post_imagecaption_home_content")
    Call<List<LocalBaseResponseModel>> homeContentPostImageCaptionRet(@Body HomeContentPostImageCaptionRequestModel model);

    @POST("set_post_comment_data")
    Call<List<HomeContentPostCommentSetResponseModel>> homeContentPostCommentRet(@Body HomeContentPostCommentSetRequestModel model);

    @POST("post_like_contentevent")
    Call<List<LocalBaseResponseModel>> homeContentPostLikeRet(@Body HomeContentPostLikeRequestModel model);

    @POST("delete_posted_data")
    Call<List<HomeContentPostCaptionDeleteResponseModel>> homeContentPostDeleteRet(@Body HomeContentPostCaptionDeleteRequestModel model);

    @POST("delete_commented_post")
    Call<List<HomeContentPostCommentDeleteResponseModel>> homeContentPostCommentDeleteRet(@Body HomeContentPostCommentDeleteRequestModel model);

    @POST("get_posted_comment_list")
    Call<List<HomeContentPostCommentGetResponseModel>> homeContentPostCommentGetRet(@Body HomeContentPostCommentGetRequestModel model);

    @POST("verification_statuslogin_appusers")
    Call<List<VerificationStatusLoginAppUserResponseModel>> verificationStatusLoginAppUserRet(@Body VerificationStatusLoginAppUserRequestModel model);

    @POST("post_feedback_the_event")
    Call<List<PostFeedbackTheEventResponseModel>> postFeedbackTheEventRet(@Body PostFeedbackTheEventRequestModel model);

    @POST("get_check_distance_location")
    Call<List<GetCheckDistanceLocationResponseModel>> getCheckDistanceLocationRet(@Body GetCheckDistanceLocationRequestModel model);

}
