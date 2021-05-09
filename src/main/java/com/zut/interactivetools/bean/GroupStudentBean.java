package com.zut.interactivetools.bean;

public class GroupStudentBean {
    private String sid;
    private String gid;

    public GroupStudentBean() {
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    @Override
    public String toString() {
        return "GroupStudentBean{" +
                "sid='" + sid + '\'' +
                ", gid='" + gid + '\'' +
                '}';
    }
}
