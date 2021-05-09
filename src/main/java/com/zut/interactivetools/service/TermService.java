package com.zut.interactivetools.service;

import com.zut.interactivetools.bean.TermBean;

import java.util.List;

public interface TermService {
    List<TermBean> queryAllTerm();

    TermBean queryNowTerm();

    String queryTermIdByCourse(String courseId, String teacherId);

    void setNowTerm(String id);

    void addTerm(String name, Integer status);


}
