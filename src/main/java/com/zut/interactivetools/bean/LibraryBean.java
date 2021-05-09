package com.zut.interactivetools.bean;

import java.util.Date;

public class LibraryBean {
    private String id;
    private TeacherBean teacher;
    private String name;
    private Date createDate;
    private Integer status;
    private String color;
    private Integer sectionSum;
    private Integer questionSum;

    public LibraryBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TeacherBean getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherBean teacher) {
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSectionSum() {
        return sectionSum;
    }

    public void setSectionSum(Integer sectionSum) {
        this.sectionSum = sectionSum;
    }

    public Integer getQuestionSum() {
        return questionSum;
    }

    public void setQuestionSum(Integer questionSum) {
        this.questionSum = questionSum;
    }

    @Override
    public String toString() {
        return "LibraryBean{" +
                "id='" + id + '\'' +
                ", teacher=" + teacher +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", status=" + status +
                ", color='" + color + '\'' +
                ", sectionSum=" + sectionSum +
                ", questionSum=" + questionSum +
                '}';
    }
}
