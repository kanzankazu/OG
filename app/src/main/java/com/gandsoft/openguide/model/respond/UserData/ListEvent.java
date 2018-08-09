package com.gandsoft.openguide.model.respond.UserData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListEvent {

    @SerializedName("event_id")
    @Expose
    public String eventId;
    @SerializedName("background")
    @Expose
    public String background;
    @SerializedName("logo")
    @Expose
    public String logo;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("group_code")
    @Expose
    public String groupCode;
    @SerializedName("group_company")
    @Expose
    public String groupCompany;
    @SerializedName("role_name")
    @Expose
    public String roleName;
    @SerializedName("wallet_data")
    @Expose
    public List<WalletDatum> walletData = null;

    /**
     * No args constructor for use in serialization
     */
    public ListEvent() {
    }

    /**
     * @param startDate
     * @param logo
     * @param title
     * @param eventId
     * @param groupCompany
     * @param status
     * @param background
     * @param groupCode
     * @param roleName
     * @param date
     * @param walletData
     */
    public ListEvent(String eventId, String background, String logo, String title, String startDate, String date, String status, String groupCode, String groupCompany, String roleName, List<WalletDatum> walletData) {
        super();
        this.eventId = eventId;
        this.background = background;
        this.logo = logo;
        this.title = title;
        this.startDate = startDate;
        this.date = date;
        this.status = status;
        this.groupCode = groupCode;
        this.groupCompany = groupCompany;
        this.roleName = roleName;
        this.walletData = walletData;
    }

}
