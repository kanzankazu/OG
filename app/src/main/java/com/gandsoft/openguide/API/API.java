package com.gandsoft.openguide.API;

import com.gandsoft.openguide.API.APIrequest.Event.EventDataRequestModel;
import com.gandsoft.openguide.API.APIrequest.Login.LoginRequestModel;
import com.gandsoft.openguide.API.APIrequest.UserData.UserDataRequestModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.Login.LoginResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserDataResponseModel;
import com.gandsoft.openguide.IConfig;

import java.util.List;

import retrofit2.Call;

/**
 * Created by glenn on 1/25/18.
 */

public class API {

    /*synchronized private static ApiServices initApiDomain(Context context) {
        setApiDomain(API_BASE_URL);
        return (ApiServices) setupApi(App.getAppComponent(), ApiServices.class);
    }

    synchronized public static void doReport(Context context, ReportRequestModel request, Callback callback) {
        initApiDomain(context).Report(request).enqueue((Callback<ReportRespondModel>) callback);
    }

    synchronized public static void doUpdate(Context context, UpdateRequestModel request, Callback callback) {
        initApiDomain(context).Update(request).enqueue((Callback<UpdateAppRespondModel>) callback);
    }

    synchronized public static void doGetOui(Context context, Callback callback) {
        initApiDomain(context).Oui().enqueue((Callback<OuiResponseModel>) callback);
    }

    public static void doLogin(Context context, LoginRequestModel request, Callback callback) {
        initApiDomain(context).Login(request).enqueue((Callback<List<LoginResponseModel>>) callback);
    }*/

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

    public static Call<List<EventDataResponseModel>> doEventDataRet(EventDataRequestModel requestModel) {
        return getAPIService().eventDataRet(requestModel);
    }

}
