package com.gandsoft.openguide.API.APIresponse.UserData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDataResponseModel  {

    public String account_id;
    public String image_url;
    public String full_name;
    public String position;
    public String phone_number;
    public String email;
    public String gender;
    public String birthday;
    public String checkin;
    public String privacy_policy;
    public String version_data;
    public String group_code;
    public String role_name;
    public List<UserListEventResponseModel> list_event = new ArrayList<>();

    public int number;
    public String image_url_local;


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getImage_url_local() {
        return image_url_local;
    }

    public void setImage_url_local(String image_url_local) {
        this.image_url_local = image_url_local;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getPrivacy_policy() {
        return privacy_policy;
    }

    public void setPrivacy_policy(String privacy_policy) {
        this.privacy_policy = privacy_policy;
    }

    public String getVersion_data() {
        return version_data;
    }

    public void setVersion_data(String version_data) {
        this.version_data = version_data;
    }

    public String getGroup_code() {
        return group_code;
    }

    public void setGroup_code(String group_code) {
        this.group_code = group_code;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public List<UserListEventResponseModel> getList_event() {
        return list_event;
    }

    public void setList_event(List<UserListEventResponseModel> list_event) {
        this.list_event = list_event;
    }

    /*@SerializedName("account_id")
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
    public List<UserListEventResponseModel> listEvent = null;

    *//**
     * No args constructor for use in serialization
     *//*
    public UserDataResponseModel() {
    }

    */
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
     *//*
    public UserDataResponseModel(String accountId, String imageUrl, String fullName, String position, String phoneNumber, String email, String gender, String birthday, String checkin, String privacyPolicy, String versionData, String groupCode, String roleName, List<UserListEventResponseModel> listEvent) {
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getPrivacyPolicy() {
        return privacyPolicy;
    }

    public void setPrivacyPolicy(String privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
    }

    public String getVersionData() {
        return versionData;
    }

    public void setVersionData(String versionData) {
        this.versionData = versionData;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<UserListEventResponseModel> getAllListEvent() {
        return listEvent;
    }

    public void setListEvent(List<UserListEventResponseModel> listEvent) {
        this.listEvent = listEvent;
    }*/


}