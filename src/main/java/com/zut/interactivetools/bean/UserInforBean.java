package com.zut.interactivetools.bean;

import java.util.List;

public class UserInforBean {
    private Integer errcode;
    private String errmsg;
    private String userid;
    private String name;
    private List<String> department;
    private String position;
    private Integer gender;
    private String avatar;
    private String thumb_avatar;

    public UserInforBean() {
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDepartment() {
        return department;
    }

    public void setDepartment(List<String> department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvater(String avatar) {
        this.avatar = avatar;
    }

    public String getThumb_avatar() {
        return thumb_avatar;
    }

    public void setThumb_avatar(String thumb_avatar) {
        this.thumb_avatar = thumb_avatar;
    }

    @Override
    public String toString() {
        return "UserInforBean{" +
                "errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                ", userid='" + userid + '\'' +
                ", name='" + name + '\'' +
                ", department=" + department +
                ", position='" + position + '\'' +
                ", gender=" + gender +
                ", avater='" + avatar + '\'' +
                ", thumb_avatar='" + thumb_avatar + '\'' +
                '}';
    }
}
