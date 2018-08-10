package com.gandsoft.openguide.model.respond.UserData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletDatum {

    @SerializedName("sort")
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

    /**
     * No args constructor for use in serialization
     */
    public WalletDatum() {
    }

    /**
     * @param notif
     * @param detail
     * @param bodyWallet
     * @param sort
     * @param type
     */
    public WalletDatum(String sort, String type, String bodyWallet, String notif, String detail) {
        super();
        this.sort = sort;
        this.type = type;
        this.bodyWallet = bodyWallet;
        this.notif = notif;
        this.detail = detail;
    }

}