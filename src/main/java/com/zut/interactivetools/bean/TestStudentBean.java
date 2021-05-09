package com.zut.interactivetools.bean;

import java.util.Date;

public class TestStudentBean {
    private TestCourseBean testCourse;
    private StudentBean student;
    private Date subDate;
    private Double score;
    private String answer;
    private Integer status;
    private Double courseScore;
    private GroupBean group;
    private Integer confirm;

    public TestStudentBean() {
    }

    public TestCourseBean getTestCourse() {
        return testCourse;
    }

    public void setTestCourse(TestCourseBean testCourse) {
        this.testCourse = testCourse;
    }

    public StudentBean getStudent() {
        return student;
    }

    public void setStudent(StudentBean student) {
        this.student = student;
    }

    public Date getSubDate() {
        return subDate;
    }

    public void setSubDate(Date subDate) {
        this.subDate = subDate;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(Double courseScore) {
        this.courseScore = courseScore;
    }

    public GroupBean getGroup() {
        return group;
    }

    public void setGroup(GroupBean group) {
        this.group = group;
    }

    public Integer getConfirm() {
        return confirm;
    }

    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
    }

    @Override
    public String toString() {
        return "TestStudentBean{" +
                "testCourse=" + testCourse +
                ", student=" + student +
                ", subDate=" + subDate +
                ", score=" + score +
                ", answer='" + answer + '\'' +
                ", status=" + status +
                ", courseScore=" + courseScore +
                ", group=" + group +
                ", confirm=" + confirm +
                '}';
    }
}
