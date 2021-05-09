package com.zut.interactivetools.bean;

import java.util.Date;
import java.util.List;

public class DiscussStudentBean {
    private DiscussBean discuss;
    private StudentBean student;
    private Date discussDate;
    private String body;
    private Double score;
    private Integer status;
    private GroupBean group;
    private Integer confirm;
    private List<DiscussBodyBean> discussBody;

    public DiscussStudentBean() {
    }

    public DiscussBean getDiscuss() {
        return discuss;
    }

    public void setDiscuss(DiscussBean discuss) {
        this.discuss = discuss;
    }

    public StudentBean getStudent() {
        return student;
    }

    public void setStudent(StudentBean student) {
        this.student = student;
    }

    public Date getDiscussDate() {
        return discussDate;
    }

    public void setDiscussDate(Date discussDate) {
        this.discussDate = discussDate;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public GroupBean getGroup() {
        return group;
    }

    public void setGroup(GroupBean group) {
        this.group = group;
    }

    public Integer getConfirm() {
        return confirm;
    }

    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
    }

    public List<DiscussBodyBean> getDiscussBody() {
        return discussBody;
    }

    public void setDiscussBody(List<DiscussBodyBean> discussBody) {
        this.discussBody = discussBody;
    }

    @Override
    public String toString() {
        return "DiscussStudentBean{" +
                "discuss=" + discuss +
                ", student=" + student +
                ", discussDate=" + discussDate +
                ", body='" + body + '\'' +
                ", score=" + score +
                ", status=" + status +
                ", group=" + group +
                ", confirm=" + confirm +
                ", discussBody=" + discussBody +
                '}';
    }
}
