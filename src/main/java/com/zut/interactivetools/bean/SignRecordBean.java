package com.zut.interactivetools.bean;

import java.util.Date;

public class SignRecordBean {
    private SignBean sign;
    private StudentBean student;
    private Date signDate;
    private String extra;
    private Integer record;
    private Integer type;
    private Integer status;
    private Double score;
    private GroupBean group;
    private Integer confirm;

    public SignRecordBean() {
    }

    public SignBean getSign() {
        return sign;
    }

    public void setSign(SignBean sign) {
        this.sign = sign;
    }

    public StudentBean getStudent() {
        return student;
    }

    public void setStudent(StudentBean student) {
        this.student = student;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Integer getRecord() {
        return record;
    }

    public void setRecord(Integer record) {
        this.record = record;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
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

    @Override
    public String toString() {
        return "SignRecordBean{" +
                "sign=" + sign +
                ", student=" + student +
                ", signDate=" + signDate +
                ", extra='" + extra + '\'' +
                ", record=" + record +
                ", type=" + type +
                ", status=" + status +
                ", score=" + score +
                ", group=" + group +
                ", confirm=" + confirm +
                '}';
    }
}
