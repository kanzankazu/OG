package com.gandsoft.openguide;

/**
 * Created by glenn on 1/25/18.
 */


public interface IConfig {

    String API_BASE_URL = "http://api.openguides.id:3000/";
    int DB_Version = 3;
    String API_IP_PUBLIC = "https://api.ipify.org/?format=json";

    int API_CALLER_REPORT = 121212;
    int API_CALLER_UPDATE = 123123;
    int API_CALLER_OUI = 112233;

    String RETURN_STATUS_SUCCESS = "success";

    String PACKAGE_NAME = App.getContext().getPackageName();
    String VERSION_NAME = BuildConfig.VERSION_NAME;
    int VERSION_CODE = BuildConfig.VERSION_CODE;

}
