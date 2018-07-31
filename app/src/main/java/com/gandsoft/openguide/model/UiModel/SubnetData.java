package com.gandsoft.openguide.model.UiModel;

public class SubnetData {
    private int idSubnet;
    private String ipSubnet;
    private String hostSubnet;
    private String macSubnet;
    private String macIdSubnet;
    private String timeSubnet;

    public SubnetData(){}

    public SubnetData(int idSubnet, String ipSubnet, String hostSubnet, String macSubnet, String macIdSubnet, String timeSubnet) {
        this.idSubnet = idSubnet;
        this.ipSubnet = ipSubnet;
        this.hostSubnet = hostSubnet;
        this.macSubnet = macSubnet;
        this.macIdSubnet = macIdSubnet;
        this.timeSubnet = timeSubnet;
    }

    public int getIdSubnet() {
        return idSubnet;
    }

    public void setIdSubnet(int idSubnet) {
        this.idSubnet = idSubnet;
    }

    public String getIpSubnet() {
        return ipSubnet;
    }

    public void setIpSubnet(String ipSubnet) {
        this.ipSubnet = ipSubnet;
    }

    public String getHostSubnet() {
        return hostSubnet;
    }

    public void setHostSubnet(String hostSubnet) {
        this.hostSubnet = hostSubnet;
    }

    public String getMacSubnet() {
        return macSubnet;
    }

    public void setMacSubnet(String macSubnet) {
        this.macSubnet = macSubnet;
    }

    public String getMacIdSubnet() {
        return macIdSubnet;
    }

    public void setMacIdSubnet(String macIdSubnet) {
        this.macIdSubnet = macIdSubnet;
    }

    public String getTimeSubnet() {
        return timeSubnet;
    }

    public void setTimeSubnet(String timeSubnet) {
        this.timeSubnet = timeSubnet;
    }
}
