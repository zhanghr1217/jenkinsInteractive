package com.zut.interactivetools.dao;

import com.zut.interactivetools.bean.TeacherBean;
import com.zut.interactivetools.bean.UserBean;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserDao {
    List<TeacherBean> queryAllTeacher(@Param("status") Integer status);

    TeacherBean loginTeacher(@Param("id") String id, @Param("password") String password);

    TeacherBean queryTeacherInfo(@Param("id") String id);

    int addTeacher(List<TeacherBean> list);

    int addTeacherUser(List<UserBean> list);

    int delTeacher(@Param("id") String id);

    int delUser(@Param("id") String id);

    int updateTeacher(@Param("id") String id, @Param("name") String name, @Param("gender") Integer gender, @Param("birthday") Date birthday,
                      @Param("tel") String tel, @Param("head") String head);

    int updateTeacherWXWork(@Param("id") String id, @Param("name") String name, @Param("gender") Integer gender, @Param("head") String head);

    int updatePassword(@Param("id") String id, @Param("old_password") String oldPassword, @Param("new_password") String newPassword);

    int checkTeacher(@Param("id") String id);

}
