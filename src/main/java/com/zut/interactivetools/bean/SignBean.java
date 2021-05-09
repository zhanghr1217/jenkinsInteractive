package com.zut.interactivetools.bean;

import java.util.Date;
import java.util.List;

public class SignBean {
    private String id;
    private String name;
    private Date begin;
    private Integer limitTime;
    private Integer type;
    private CourseBean course;
    private TeacherBean teacher;
    private Integer status;
    private Double score;
    private String groupId;
    private Integer alive;
    private List<String> groupName;
    private Integer confirm;
    private Integer submitStatus;
    private Integer signCount;
    private Integer notSignCount;

    public SignBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Integer getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Integer limitTime) {
        this.limitTime = limitTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public CourseBean getCourse() {
        return course;
    }

    public void setCourse(CourseBean course) {
        this.course = course;
    }

    public TeacherBean getTeacher() {
        return teacher;
    }

    public Integer getSignCount() {
        return signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }

    public Integer getNotSignCount() {
        return notSignCount;
    }

    public void setNotSignCount(Integer notSignCount) {
        this.notSignCount = notSignCount;
    }

    public void setTeacher(TeacherBean teacher) {
        this.teacher = teacher;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getAlive() {
        return alive;
    }

    public void setAlive(Integer alive) {
        this.alive = alive;
    }

    public List<String> getGroupName() {
        return groupName;
    }

    public void setGroupName(List<String> groupName) {
        this.groupName = groupName;
    }

    public Integer getConfirm() {
        return confirm;
    }

    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
    }

    public Integer getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(Integer submitStatus) {
        this.submitStatus = submitStatus;
    }

    @Override
    public String toString() {
        return "SignBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", begin=" + begin +
                ", limitTime=" + limitTime +
                ", type=" + type +
                ", course=" + course +
                ", teacher=" + teacher +
                ", status=" + status +
                ", score=" + score +
                ", groupId='" + groupId + '\'' +
                ", alive=" + alive +
                ", groupName=" + groupName +
                ", confirm=" + confirm +
                ", submitStatus=" + submitStatus +
                '}';
    }
}
