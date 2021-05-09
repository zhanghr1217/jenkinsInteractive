package com.zut.interactivetools.service;

import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.CourseBean;

import java.util.List;

public interface CourseService {
    List<CourseBean> queryCourseByTeacher(String teacherId, Integer termStatus, Integer status);

    void addCourseByTeacher(String id, String number, String name, String teacherId, String termId, String img);

    void delCourseByTeacher(String id, String teacherId);

    void updateCourseByTeacher(String id, String teacherId, String name, String img, String number);

    CourseBean queryCourseInfoById(String id, String teacherId);
}
