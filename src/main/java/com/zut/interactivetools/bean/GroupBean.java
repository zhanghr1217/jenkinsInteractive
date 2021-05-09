package com.zut.interactivetools.bean;

import java.util.Date;

public class GroupBean {
    private String id;
    private String name;
    private TeacherBean teacher;
    private CourseBean course;
    private TermBean term;
    private Integer status;
    private Date createDate;
    private Integer sum;

    public GroupBean() {
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

    public TermBean getTerm() {
        return term;
    }

    public void setTerm(TermBean term) {
        this.term = term;
    }

    public Integer getStatus() {
        return status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "GroupBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                ", course=" + course +
                ", term=" + term +
                ", status=" + status +
                ", createDate=" + createDate +
                ", sum=" + sum +
                '}';
    }
}
