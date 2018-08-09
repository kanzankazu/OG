package com.gandsoft.openguide.model.respond.UserData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserdataResponseModel {

    @SerializedName("account_id")
    @Expose
    public String accountId;
    @SerializedName("image_url")
    @Expose
    public String imageUrl;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("position")
    @Expose
    public String position;
    @SerializedName("phone_number")
    @Expose
    public String phoneNumber;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("birthday")
    @Expose
    public String birthday;
    @SerializedName("checkin")
    @Expose
    public String checkin;
    @SerializedName("privacy_policy")
    @Expose
    public String privacyPolicy;
    @SerializedName("version_data")
    @Expose
    public String versionData;
    @SerializedName("group_code")
    @Expose
    public String groupCode;
    @SerializedName("role_name")
    @Expose
    public String roleName;
    @SerializedName("list_event")
    @Expose
    public List<ListEvent> listEvent = null;

    /**
     * No args constructor for use in serialization
     */
    public UserdataResponseModel() {
    }

    /**
     * @param position
     * @param listEvent
     * @param birthday
     * @param versionData
     * @param accountId
     * @param privacyPolicy
     * @param imageUrl
     * @param groupCode
     * @param roleName
     * @param checkin
     * @param phoneNumber
     * @param email
     * @param gender
     * @param fullName
     */
    public UserdataResponseModel(String accountId, String imageUrl, String fullName, String position, String phoneNumber, String email, String gender, String birthday, String checkin, String privacyPolicy, String versionData, String groupCode, String roleName, List<ListEvent> listEvent) {
        super();
        this.accountId = accountId;
        this.imageUrl = imageUrl;
        this.fullName = fullName;
        this.position = position;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.checkin = checkin;
        this.privacyPolicy = privacyPolicy;
        this.versionData = versionData;
        this.groupCode = groupCode;
        this.roleName = roleName;
        this.listEvent = listEvent;
    }

}