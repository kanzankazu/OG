package com.gandsoft.openguide.API.APIresponse.UserData;

public class UserWalletDataResponseModel {

    public String sort;
    public String type;
    public String body_wallet;
    public String notif;
    public String detail;

    public int number;
    public String accountId;
    public String eventId;
    public String id_card_local;

    public String getId_card_local() {
        return id_card_local;
    }

    public void setId_card_local(String id_card_local) {
        this.id_card_local = id_card_local;
    }

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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody_wallet() {
        return body_wallet;
    }

    public void setBody_wallet(String body_wallet) {
        this.body_wallet = body_wallet;
    }

    public String getNotif() {
        return notif;
    }

    public void setNotif(String notif) {
        this.notif = notif;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    /*@SerializedName("sort")
    @Expose
    public String sort;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("body_wallet")
    @Expose
    public String bodyWallet;
    @SerializedName("notif")
    @Expose
    public String notif;
    @SerializedName("detail")
    @Expose
    public String detail;

    *//**
     * No args constructor for use in serialization
     *//*
    public UserWalletDataResponseModel() {
    }

    *//**
     * @param notif
     * @param detail
     * @param bodyWallet
     * @param sort
     * @param type
     *//*
    public UserWalletDataResponseModel(String sort, String type, String bodyWallet, String notif, String detail) {
        super();
        this.sort = sort;
        this.type = type;
        this.bodyWallet = bodyWallet;
        this.notif = notif;
        this.detail = detail;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBodyWallet() {
        return bodyWallet;
    }

    public void setBodyWallet(String bodyWallet) {
        this.bodyWallet = bodyWallet;
    }

    public String getNotif() {
        return notif;
    }

    public void setNotif(String notif) {
        this.notif = notif;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }*/
}