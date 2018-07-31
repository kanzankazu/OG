package com.gandsoft.openguide.API;

import android.content.Context;

import com.gandsoft.openguide.App;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.model.request.Report.ReportRequestModel;
import com.gandsoft.openguide.model.request.Update.UpdateRequestModel;
import com.gandsoft.openguide.model.respond.Oui.OuiResponseModel;
import com.gandsoft.openguide.model.respond.Report.ReportRespondModel;
import com.gandsoft.openguide.model.respond.Update.UpdateAppRespondModel;

import app.beelabs.com.codebase.base.BaseApi;
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

}
