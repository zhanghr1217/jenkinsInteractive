package com.zut.interactivetools.bean;

import java.util.Date;

public class LotteryBean {
    private String id;
    private StudentBean student;
    private Date createDate;
    private TeacherBean teacher;
    private CourseBean course;
    private Integer status;
    private String groupId;

    public LotteryBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StudentBean getStudent() {
        return student;
    }

    public void setStudent(StudentBean student) {
        this.student = student;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public TeacherBean getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherBean teacher) {
        this.teacher = teacher;
    }

    public CourseBean getCourse() {
        return course;
    }

    public void setCourse(CourseBean course) {
        this.course = course;
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

    @Override
    public String toString() {
        return "LotteryBean{" +
                "id='" + id + '\'' +
                ", student=" + student +
                ", createDate=" + createDate +
                ", teacher=" + teacher +
                ", course=" + course +
                ", status=" + status +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}
