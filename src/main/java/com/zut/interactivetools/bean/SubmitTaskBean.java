package com.zut.interactivetools.bean;

import java.io.Serializable;

/**
 * @program: interactivetools_integration
 * @description: 定时提交任务
 * @author: zjj
 * @create: 2021-02-05 17:51
 **/
public class SubmitTaskBean implements Serializable {

    private String id;
    private long taskTimestamp;
    private TaskEnum taskEnum;
    private String needSendUser;
    private String teacherId;
    private String courseId;
    private String courseNum;
    private String teacherName;
    private String courseName;

    public SubmitTaskBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public long getTaskTimestamp() {
        return taskTimestamp;
    }

    public void setTaskTimestamp(long taskTimestamp) {
        this.taskTimestamp = taskTimestamp;
    }

    public TaskEnum getTaskEnum() {
        return taskEnum;
    }

    public void setTaskEnum(TaskEnum taskEnum) {
        this.taskEnum = taskEnum;
    }

    public String getNeedSendUser() {
        return needSendUser;
    }

    public void setNeedSendUser(String needSendUser) {
        this.needSendUser = needSendUser;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    @Override
    public String toString() {
        return "SubmitTaskBean{" +
                "id='" + id + '\'' +
                ", taskTimestamp=" + taskTimestamp +
                ", taskEnum=" + taskEnum +
                ", needSendUser='" + needSendUser + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", courseNum='" + courseNum + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", courseName='" + courseName + '\'' +
                '}';
    }
}
