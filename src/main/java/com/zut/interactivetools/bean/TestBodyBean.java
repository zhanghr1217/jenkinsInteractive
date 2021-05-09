package com.zut.interactivetools.bean;

import java.util.List;

public class TestBodyBean {
    private String question;
    private Integer type;
    private Double score;
    private Integer maxNum;
    private List<AnswerBean> answer;

    public TestBodyBean() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<AnswerBean> getAnswer() {
        return answer;
    }

    public void setAnswer(List<AnswerBean> answer) {
        this.answer = answer;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    @Override
    public String toString() {
        return "TestBodyBean{" +
                "question='" + question + '\'' +
                ", type=" + type +
                ", score=" + score +
                ", maxNum=" + maxNum +
                ", answer=" + answer +
                '}';
    }
}
