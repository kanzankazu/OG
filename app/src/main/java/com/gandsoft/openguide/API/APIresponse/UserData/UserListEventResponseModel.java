package com.gandsoft.openguide.API.APIresponse.UserData;


import java.util.ArrayList;
import java.util.List;

public class UserListEventResponseModel  {

    public String event_id;
    public String background;
    public String logo;
    public String title;
    public String start_date;
    public String date;
    public String status;
    public String group_code;
    public String group_company;
    public String role_name;

    public int number;
    public String accountId;
    public String logo_local;
    public String background_local;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getLogo_local() {
        return logo_local;
    }

    public void setLogo_local(String logo_local) {
        this.logo_local = logo_local;
    }

    public String getBackground_local() {
        return background_local;
    }

    public void setBackground_local(String background_local) {
        this.background_local = background_local;
    }

    public List<UserWalletDataResponseModel> wallet_data = new ArrayList<>();

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroup_code() {
        return group_code;
    }

    public void setGroup_code(String group_code) {
        this.group_code = group_code;
    }

    public String getGroup_company() {
        return group_company;
    }

    public void setGroup_company(String group_company) {
        this.group_company = group_company;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public List<UserWalletDataResponseModel> getWallet_data() {
        return wallet_data;
    }

    public void setWallet_data(List<UserWalletDataResponseModel> wallet_data) {
        this.wallet_data = wallet_data;
    }

    /*@SerializedName("event_id")
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
    public List<UserWalletDataResponseModel> walletData = null;

    *//**
     * No args constructor for use in serialization
     *//*
    public UserListEventResponseModel() {
    }

    *//**
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
     *//*
    public UserListEventResponseModel(String eventId, String background, String logo, String title, String startDate, String date, String status, String groupCode, String groupCompany, String roleName, List<UserWalletDataResponseModel> walletData) {
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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupCompany() {
        return groupCompany;
    }

    public void setGroupCompany(String groupCompany) {
        this.groupCompany = groupCompany;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<UserWalletDataResponseModel> getWalletData() {
        return walletData;
    }

    public void setWalletData(List<UserWalletDataResponseModel> walletData) {
        this.walletData = walletData;
    }*/
}
