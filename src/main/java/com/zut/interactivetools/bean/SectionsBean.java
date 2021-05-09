package com.zut.interactivetools.bean;

import java.util.Date;
import java.util.List;

public class SectionsBean {
    private String id;
    private LibraryBean library;
    private String name;
    private String title;
    private Integer status;
    private Date createDate;
    private List<QuestionBean> questions;

    public SectionsBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LibraryBean getLibrary() {
        return library;
    }

    public void setLibrary(LibraryBean library) {
        this.library = library;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<QuestionBean> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionBean> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "SectionsBean{" +
                "id='" + id + '\'' +
                ", library=" + library +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", createDate=" + createDate +
                ", questions=" + questions +
                '}';
    }
}
