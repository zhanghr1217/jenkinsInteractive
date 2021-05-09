package com.zut.interactivetools.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class TextCardBean {
    private String touser;
    private String toparty;
    private String totag;
    private String msgtype = "textcard";
    private Integer agentid;
    @JSONField(name = "textcard")
    private TextCardInfoBean textCard;
    private int enable_id_trans = 0;
    private int enable_duplicate_check = 0;
    private int duplicate_check_interval = 1800;

    public TextCardBean() {
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getToparty() {
        return toparty;
    }

    public void setToparty(String toparty) {
        this.toparty = toparty;
    }

    public String getTotag() {
        return totag;
    }

    public void setTotag(String totag) {
        this.totag = totag;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Integer getAgentid() {
        return agentid;
    }

    public void setAgentid(Integer agentid) {
        this.agentid = agentid;
    }

    public TextCardInfoBean getTextCard() {
        return textCard;
    }

    public void setTextCard(TextCardInfoBean textCard) {
        this.textCard = textCard;
    }

    public int getEnable_id_trans() {
        return enable_id_trans;
    }

    public void setEnable_id_trans(int enable_id_trans) {
        this.enable_id_trans = enable_id_trans;
    }

    public int getEnable_duplicate_check() {
        return enable_duplicate_check;
    }

    public void setEnable_duplicate_check(int enable_duplicate_check) {
        this.enable_duplicate_check = enable_duplicate_check;
    }

    public int getDuplicate_check_interval() {
        return duplicate_check_interval;
    }

    public void setDuplicate_check_interval(int duplicate_check_interval) {
        this.duplicate_check_interval = duplicate_check_interval;
    }

    @Override
    public String toString() {
        return "TextCardBean{" +
                "touser='" + touser + '\'' +
                ", toparty='" + toparty + '\'' +
                ", totag='" + totag + '\'' +
                ", msgtype='" + msgtype + '\'' +
                ", agentid=" + agentid +
                ", textCard=" + textCard +
                ", enable_id_trans=" + enable_id_trans +
                ", enable_duplicate_check=" + enable_duplicate_check +
                ", duplicate_check_interval=" + duplicate_check_interval +
                '}';
    }
}
