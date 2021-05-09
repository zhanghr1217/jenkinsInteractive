package com.zut.interactivetools.service;

import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.TeacherBean;
import com.zut.interactivetools.bean.UserBean;

import java.util.Date;
import java.util.List;

public interface UserService {
    PageInfo<TeacherBean> queryAllTeacher(int pageNum, int pageSize, Integer status);

    TeacherBean loginTeacher(String id, String password);

    TeacherBean queryTeacherInfo(String id);

    void addTeacherUser(List<TeacherBean> teacherBeans);

    void delTeacher(String id);


    void updateTeacher(String id, String name, Integer gender, Date birthday, String tel, String head);

    void updatePassword(String id, String oldPassword, String newPassword);

    int updateTeacherWXWork(String id, String name,Integer gender, String head);
}
