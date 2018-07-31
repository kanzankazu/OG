package com.gandsoft.openguide.API;

import com.gandsoft.openguide.model.request.Report.ReportRequestModel;
import com.gandsoft.openguide.model.request.Update.UpdateRequestModel;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BaseDao;
import retrofit2.Callback;

/**
 * Created by glenn on 1/25/18.
 */

public class ApiDao extends BaseDao {

    public ApiDao(Object obj) {
        super(obj);
    }

    public void doReportDao(BaseActivity ac, ReportRequestModel request, Callback callback) {
        API.doReport(ac, request, callback);
    }

    public void doUpdateDao(BaseActivity ac, UpdateRequestModel request, Callback callback) {
        API.doUpdate(ac, request, callback);
    }

    public void doGetOuiDao(BaseActivity ac, Callback callback) {
        API.doGetOui(ac, callback);
    }

}
