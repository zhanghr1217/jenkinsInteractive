package com.zut.interactivetools.bean;

import java.util.Date;
import java.util.List;

public class DiscussCourseBean {
    private String id;
    private String name;
    private DiscussBean discuss;
    private CourseBean course;
    private TeacherBean teacher;
    private Date createDate;
    private Integer status;
    private String groupId;
    private String discussBody;
    private Integer limit;
    private Integer alive;
    private List<String> groupName;
    private Integer confirm;

    public DiscussCourseBean() {
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

    public DiscussBean getDiscuss() {
        return discuss;
    }

    public void setDiscuss(DiscussBean discuss) {
        this.discuss = discuss;
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

    public void setTeacher(TeacherBean teacher) {
        this.teacher = teacher;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDiscussBody() {
        return discussBody;
    }

    public void setDiscussBody(String discussBody) {
        this.discussBody = discussBody;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
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

    @Override
    public String toString() {
        return "DiscussCourseBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", discuss=" + discuss +
                ", course=" + course +
                ", teacher=" + teacher +
                ", createDate=" + createDate +
                ", status=" + status +
                ", groupId='" + groupId + '\'' +
                ", discussBody='" + discussBody + '\'' +
                ", limit=" + limit +
                ", alive=" + alive +
                ", groupName=" + groupName +
                ", confirm=" + confirm +
                '}';
    }
}
