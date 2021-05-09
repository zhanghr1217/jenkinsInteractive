package com.zut.interactivetools.bean;

import java.util.Date;

public class SignQRCodeBean {
    private String id;
    private String token;
    private Date date;
    private Integer alive;
    private String sign_id;
    private Integer status;

    public SignQRCodeBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAlive() {
        return alive;
    }

    public void setAlive(Integer alive) {
        this.alive = alive;
    }

    public String getSign_id() {
        return sign_id;
    }

    public void setSign_id(String sign_id) {
        this.sign_id = sign_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SignQRCodeBean{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", date=" + date +
                ", alive=" + alive +
                ", sign_id='" + sign_id + '\'' +
                ", status=" + status +
                '}';
    }
}
