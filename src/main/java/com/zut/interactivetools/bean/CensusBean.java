package com.zut.interactivetools.bean;

import java.util.List;

/**
 * @program: interactivetools_integration
 * @description: 签到统计
 * @author: zjj
 * @create: 2021-02-17 17:37
 **/
public class CensusBean {
    private List<StudentBean> students;
    private List<SignBean> signs;

    public CensusBean() {
    }

    public List<StudentBean> getStudents() {
        return students;
    }

    public void setStudents(List<StudentBean> students) {
        this.students = students;
    }

    public List<SignBean> getSigns() {
        return signs;
    }

    public void setSigns(List<SignBean> signs) {
        this.signs = signs;
    }

    @Override
    public String toString() {
        return "CensusBean{" +
                "students=" + students +
                ", signs=" + signs +
                '}';
    }
}
