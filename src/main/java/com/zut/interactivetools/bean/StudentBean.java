package com.zut.interactivetools.bean;

import java.util.Date;
import java.util.List;

public class StudentBean {
    private String id;
    private String name;
    private Integer gender;
    private String head;
    private String institute;
    private String theClass;
    private Integer status;
    private Date birthday;
    private String major;
    private Double score;
    private String groupId;
    private List<String> groupName;
    private String extra;
    private String simpleGroupName;
    private Integer record;
    private Date signDate;
    private Integer signCount;
    private Integer notSignCount;

    public StudentBean() {
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

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getTheClass() {
        return theClass;
    }

    public void setTheClass(String theClass) {
        this.theClass = theClass;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
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

    public List<String> getGroupName() {
        return groupName;
    }

    public void setGroupName(List<String> groupName) {
        this.groupName = groupName;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getSimpleGroupName() {
        return simpleGroupName;
    }

    public void setSimpleGroupName(String simpleGroupName) {
        this.simpleGroupName = simpleGroupName;
    }

    public Integer getRecord() {
        return record;
    }

    public void setRecord(Integer record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "StudentBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", head='" + head + '\'' +
                ", institute='" + institute + '\'' +
                ", theClass='" + theClass + '\'' +
                ", status=" + status +
                ", birthday=" + birthday +
                ", major='" + major + '\'' +
                ", score=" + score +
                ", groupId='" + groupId + '\'' +
                ", groupName=" + groupName +
                ", extra='" + extra + '\'' +
                ", simpleGroupName='" + simpleGroupName + '\'' +
                ", record=" + record +
                '}';
    }
}
