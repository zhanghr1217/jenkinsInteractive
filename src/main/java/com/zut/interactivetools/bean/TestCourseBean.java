package com.zut.interactivetools.bean;

import java.util.Date;
import java.util.List;

public class TestCourseBean {
    private String id;
    private TestBean test;
    private CourseBean course;
    private TeacherBean teacher;
    private String groupId;
    private Integer status;
    private String group;
    private String name;
    private Date createDate;
    private String testBody;
    private Integer alive;
    private Integer limitTime;
    private String testAnswer;
    private List<String> groupName;
    private Integer confirm;
    private Integer submitStatus;

    public TestCourseBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TestBean getTest() {
        return test;
    }

    public void setTest(TestBean test) {
        this.test = test;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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

    public String getTestBody() {
        return testBody;
    }

    public void setTestBody(String testBody) {
        this.testBody = testBody;
    }

    public Integer getAlive() {
        return alive;
    }

    public void setAlive(Integer alive) {
        this.alive = alive;
    }

    public Integer getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Integer limitTime) {
        this.limitTime = limitTime;
    }

    public String getTestAnswer() {
        return testAnswer;
    }

    public void setTestAnswer(String testAnswer) {
        this.testAnswer = testAnswer;
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

    public Integer getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(Integer submitStatus) {
        this.submitStatus = submitStatus;
    }

    @Override
    public String toString() {
        return "TestCourseBean{" +
                "id='" + id + '\'' +
                ", test=" + test +
                ", course=" + course +
                ", teacher=" + teacher +
                ", groupId='" + groupId + '\'' +
                ", status=" + status +
                ", group='" + group + '\'' +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", testBody='" + testBody + '\'' +
                ", alive=" + alive +
                ", limitTime=" + limitTime +
                ", testAnswer='" + testAnswer + '\'' +
                ", groupName=" + groupName +
                ", confirm=" + confirm +
                ", submitStatus=" + submitStatus +
                '}';
    }
}
