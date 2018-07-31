package com.gandsoft.openguide.model.respond.Report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gandsoft.openguide.model.respond.BaseResponseModel;
import com.gandsoft.openguide.model.respond.Update.UpdateAppItemModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportRespondModel extends BaseResponseModel {

    private UpdateAppItemModel data_app = new UpdateAppItemModel();

    public UpdateAppItemModel getData_app() {
        return data_app;
    }

    public void setData_app(UpdateAppItemModel data_app) {
        this.data_app = data_app;
    }
}
