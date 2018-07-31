package com.gandsoft.openguide.model.respond.Oui;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gandsoft.openguide.model.respond.BaseResponseModel;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OuiResponseModel extends BaseResponseModel {
    private ArrayList<OuiItemModel> data ;

    public ArrayList<OuiItemModel> getData() {
        return data;
    }

    public void setData(ArrayList<OuiItemModel> data) {
        this.data = data;
    }
}
