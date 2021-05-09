package com.zut.interactivetools.bean;

public class UserIdBean {
    private Integer errcode;
    private String errmsg;
//    @JSONField(name = "UserId")
    private String UserId;
//    @JSONField(name = "DeviceId")
    private String DeviceId;

    public UserIdBean() {
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    @Override
    public String toString() {
        return "UserIdBean{" +
                "errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                ", UserId='" + UserId + '\'' +
                ", DeviceId='" + DeviceId + '\'' +
                '}';
    }
}
