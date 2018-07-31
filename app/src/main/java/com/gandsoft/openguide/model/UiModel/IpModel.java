package com.gandsoft.openguide.model.UiModel;

/**
 * Created by kanzan on 3/1/2018.
 */

public class IpModel {
    private int ipId;
    private String ip;
    private String ipDesc;
    private String ipUsername;
    private String ipPassword;

    private String ipLocal;
    private String ipPublic;
    private String ipGateway;
    private String ipDns1;
    private String ipDns2;
    private String ipNetMask;
    private int itemResourceID;
    private int itemContainerID;

    public IpModel() {
    }

    public IpModel(String ip) {
        this.ip = ip;
    }

    public IpModel(String ip, String ipDesc) {
        this.ip = ip;
        this.ipDesc = ipDesc;
    }

    public IpModel(int ipId, String ip, String ipDesc) {
        this.ipId = ipId;
        this.ip = ip;
        this.ipDesc = ipDesc;
    }

    public IpModel(int ipId, String ip, String ipDesc, String ipUsername, String ipPassword) {
        this.ipId = ipId;
        this.ip = ip;
        this.ipDesc = ipDesc;
        this.ipUsername = ipUsername;
        this.ipPassword = ipPassword;
    }

    public String getIpLocal() {
        return ipLocal;
    }

    public void setIpLocal(String ipLocal) {
        this.ipLocal = ipLocal;
    }

    public String getIpPublic() {
        return ipPublic;
    }

    public void setIpPublic(String ipPublic) {
        this.ipPublic = ipPublic;
    }

    public String getIpGateway() {
        return ipGateway;
    }

    public void setIpGateway(String ipGateway) {
        this.ipGateway = ipGateway;
    }

    public String getIpDns1() {
        return ipDns1;
    }

    public void setIpDns1(String ipDns1) {
        this.ipDns1 = ipDns1;
    }

    public String getIpDns2() {
        return ipDns2;
    }

    public void setIpDns2(String ipDns2) {
        this.ipDns2 = ipDns2;
    }

    public String getIpNetMask() {
        return ipNetMask;
    }

    public void setIpNetMask(String ipNetMask) {
        this.ipNetMask = ipNetMask;
    }

    public int getIpId() {
        return ipId;
    }

    public void setIpId(int idIp) {
        this.ipId = idIp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpDesc() {
        return ipDesc;
    }

    public void setIpDesc(String ipDesc) {
        this.ipDesc = ipDesc;
    }

    public String getIpUsername() {
        return ipUsername;
    }

    public void setIpUsername(String ipUsername) {
        this.ipUsername = ipUsername;
    }

    public String getIpPassword() {
        return ipPassword;
    }

    public void setIpPassword(String ipPassword) {
        this.ipPassword = ipPassword;
    }

    public int getItemResourceID() {
        return itemResourceID;
    }

    public void setItemResourceID(int itemResourceID) {
        this.itemResourceID = itemResourceID;
    }

    public int getItemContainerID() {

        return itemContainerID;
    }

    public void setItemContainerID(int itemContainerID) {
        this.itemContainerID = itemContainerID;
    }
}
