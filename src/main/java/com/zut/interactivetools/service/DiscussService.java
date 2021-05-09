package com.zut.interactivetools.service;

import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.DiscussBean;
import com.zut.interactivetools.bean.DiscussCourseBean;
import com.zut.interactivetools.bean.DiscussStudentBean;
import com.zut.interactivetools.bean.GroupStudentBean;

import java.util.Date;
import java.util.List;

public interface DiscussService {
    PageInfo<DiscussBean> queryDiscussByCourse(int pageNum, int pageSize, String courseId, String teacherId, Integer status, String discussId,
                                               String discussName, String theme, Date date, Double score);

    DiscussBean queryDiscussById(String courseId, String teacherId, String id);

    void delDiscussByCourse(String id, String courseId, String teacherId);

    void updateDiscussByCourse(String id, String courseId, String teacherId, String name, String theme, Double score);

    void addDiscussByCourse(String courseId, String teacherId, String name, String theme, Date date, Double score);

    PageInfo<DiscussCourseBean> queryDiscussCourseByCourse(int pageNum, int pageSize, String courseId, String teacherId,
                                                           Integer status, String id, String name, String date);

    DiscussCourseBean queryDiscussCourseThemeByIdTeacher(String id, String teacherId);

    DiscussCourseBean queryDiscussCourseThemeByIdStudent(String id);

    void addDiscussCourseByCourse(String id, String courseId, String teacherId, String name, String discussBody, Date date, String groupId);

    void delDiscussCourseByCourse(String id, String courseId, String teacherId);

    void updateDiscussCourseByCourse(String id, String courseId, String teacherId, String name);

    void outDiscussCourseByCourse(String id, String courseId, String teacherId);

    List<DiscussStudentBean> queryDiscussStudentByCourse(String discussId, Integer status, String teacherId, String courseId);

    List<DiscussStudentBean> queryDiscussStudentInfoByDiscuss(String discussId, Integer status, String teacherId, String courseId);

    List<String> queryDiscussStudentBodyByStudent(String discussId, Integer status, String studentId);

    void addDiscussStudentByCourse(String discussId, List<GroupStudentBean> list, String teacherId, String courseId);

    void delDiscussStudentByCourse(String discussId, String studentId, String groupId, String teacherId, String courseId);


    void updateDiscussStudentByCourse(String discussId, String studentId, String groupId, Double score, String teacherId, String courseId);

    void updateDiscussStudentByStudent(String discussId, String studentId, String body);

    void confirmDiscussStudentByCourse(Double score, String discussId, String studentId, String groupId);

    void confirmDiscuss(String id, String courseId, String teacherId);

}
