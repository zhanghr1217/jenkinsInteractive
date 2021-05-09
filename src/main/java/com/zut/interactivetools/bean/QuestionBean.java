package com.zut.interactivetools.bean;

import java.util.Date;
import java.util.List;

public class QuestionBean {
    private String id;
    private String name;
    private String question;
    private String answer;
    private String realAnswer;
    private Integer type;
    private LibraryBean library;
    private SectionsBean sections;
    private TeacherBean teacher;
    private Date createDate;
    private Double score;
    private Integer status;
    private List<AnswerBean> answerBeans;
    private List<String> realBeans;
    private Integer maxNum;

    public QuestionBean() {
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getRealAnswer() {
        return realAnswer;
    }

    public void setRealAnswer(String realAnswer) {
        this.realAnswer = realAnswer;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public LibraryBean getLibrary() {
        return library;
    }

    public void setLibrary(LibraryBean library) {
        this.library = library;
    }

    public SectionsBean getSections() {
        return sections;
    }

    public void setSections(SectionsBean sections) {
        this.sections = sections;
    }

    public List<AnswerBean> getAnswerBeans() {
        return answerBeans;
    }

    public void setAnswerBeans(List<AnswerBean> answerBeans) {
        this.answerBeans = answerBeans;
    }

    public List<String> getRealBeans() {
        return realBeans;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public void setRealBeans(List<String> realBeans) {
        this.realBeans = realBeans;
    }

    @Override
    public String toString() {
        return "QuestionBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", realAnswer='" + realAnswer + '\'' +
                ", type=" + type +
                ", library=" + library +
                ", sections=" + sections +
                ", teacher=" + teacher +
                ", createDate=" + createDate +
                ", score=" + score +
                ", status=" + status +
                ", answerBeans=" + answerBeans +
                ", realBeans=" + realBeans +
                ", maxNum=" + maxNum +
                '}';
    }
}
