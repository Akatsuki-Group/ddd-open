package com.tuling.tulingmall.open.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author roy
 * @desc
 */
@TableName("clientinfo")
public class ClientInfo {

    private String sysId;

    private String sysName;

    private String privatekey;

    private String publickey;

    private String authedIp;

    private String userName;

    private String pwd;

    private String notifyUrl;

    private String notifyParam;

    private String deleteFlag;

    private String reserve1;

    private String reserve2;

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public String getAuthedIp() {
        return authedIp;
    }

    public void setAuthedIp(String authedIp) {
        this.authedIp = authedIp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getNotifyParam() {
        return notifyParam;
    }

    public void setNotifyParam(String notifyParam) {
        this.notifyParam = notifyParam;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }

    @Override
    public String toString() {
        return "ClientInfo{" +
                "sysId='" + sysId + '\'' +
                ", sysName='" + sysName + '\'' +
                ", privatekey='" + privatekey + '\'' +
                ", publickey='" + publickey + '\'' +
                ", authedIp='" + authedIp + '\'' +
                ", userName='" + userName + '\'' +
                ", pwd='" + pwd + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", notifyParam='" + notifyParam + '\'' +
                ", deleteFlag='" + deleteFlag + '\'' +
                ", reserve1='" + reserve1 + '\'' +
                ", reserve2='" + reserve2 + '\'' +
                '}';
    }
}
