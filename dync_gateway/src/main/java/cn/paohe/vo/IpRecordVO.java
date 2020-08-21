package cn.paohe.vo;

import javax.persistence.Id;

public class IpRecordVO {

    @Id
    private String id;

    private String ip;

    private String macAddress;

    private String modelCode;

    private String uri;

    private String recodeDate;

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getRecodeDate() {
        return recodeDate;
    }

    public void setRecodeDate(String recodeDate) {
        this.recodeDate = recodeDate;
    }

}
