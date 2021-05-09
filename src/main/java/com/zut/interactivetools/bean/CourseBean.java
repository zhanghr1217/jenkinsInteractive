package com.zut.interactivetools.bean;

public class CourseBean {
    private String id;
    private String name;
    private TeacherBean teacher;
    private TermBean term;
    private String img;
    private String number;
    private Integer status;

    public CourseBean() {
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

    public TermBean getTerm() {
        return term;
    }

    public void setTerm(TermBean term) {
        this.term = term;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CourseBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                ", term=" + term +
                ", img='" + img + '\'' +
                ", number='" + number + '\'' +
                ", status=" + status +
                '}';
    }
}
