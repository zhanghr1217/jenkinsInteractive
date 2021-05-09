package com.zut.interactivetools.bean;

public class StudentLotteryBean {
    private String name;
    private String avatar;
    private LotteryDataBean data;

    public StudentLotteryBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LotteryDataBean getData() {
        return data;
    }

    public void setData(LotteryDataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StudentLotteryBean{" +
                "name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", data=" + data +
                '}';
    }
}
