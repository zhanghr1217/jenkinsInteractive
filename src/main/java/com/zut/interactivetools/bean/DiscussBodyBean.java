package com.zut.interactivetools.bean;

import java.util.Date;

public class DiscussBodyBean {
    private String body;
    private Date date;

    public DiscussBodyBean() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DiscussBodyBean{" +
                "body='" + body + '\'' +
                ", date=" + date +
                '}';
    }
}
