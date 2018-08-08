package com.gandsoft.openguide.API;

import android.content.Context;
import android.util.Log;

import com.gandsoft.openguide.App;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.model.request.Login.LoginRequestModel;
import com.gandsoft.openguide.model.request.Report.ReportRequestModel;
import com.gandsoft.openguide.model.request.Update.UpdateRequestModel;
import com.gandsoft.openguide.model.respond.Login.LoginResponseModel;
import com.gandsoft.openguide.model.respond.Oui.OuiResponseModel;
import com.gandsoft.openguide.model.respond.Report.ReportRespondModel;
import com.gandsoft.openguide.model.respond.Update.UpdateAppRespondModel;

import java.util.ArrayList;

import app.beelabs.com.codebase.base.BaseApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by glenn on 1/25/18.
 */

public class API extends BaseApi implements IConfig {

    synchronized private static ApiServices initApiDomain(Context context) {
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

    synchronized public static void doLogin(Context context, LoginRequestModel request, Callback callback) {
        initApiDomain(context).Login(request).enqueue((Callback<ArrayList<LoginResponseModel>>) callback);
        Log.d("Lihat doLogin2 API", String.valueOf(initApiDomain(context).Login(request)));
    }

    synchronized public static void doLogin2(Context context, LoginRequestModel request, Callback callback) {
        initApiDomain(context).Login2(request).enqueue((Callback<LoginResponseModel>) callback);
        Log.d("Lihat doLogin2 API", String.valueOf(initApiDomain(context).Login2(request)));
    }

}
