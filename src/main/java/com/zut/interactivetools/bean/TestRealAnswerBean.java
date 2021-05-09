package com.zut.interactivetools.bean;

import java.util.List;

public class TestRealAnswerBean {
    private List<String> realAnswer;

    public TestRealAnswerBean() {
    }

    public List<String> getRealAnswer() {
        return realAnswer;
    }

    public void setRealAnswer(List<String> realAnswer) {
        this.realAnswer = realAnswer;
    }

    @Override
    public String toString() {
        return "TestRealAnswerBean{" +
                "realAnswer=" + realAnswer +
                '}';
    }
}
