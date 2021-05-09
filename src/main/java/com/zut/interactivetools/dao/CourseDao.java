package com.zut.interactivetools.dao;

import com.zut.interactivetools.bean.CourseBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CourseDao {
    List<CourseBean> queryCourseByTeacher(@Param("teacher_id") String teacherId, @Param("term_status") Integer termStatus, @Param("status") Integer status);

    int addCourseByTeacher(@Param("id") String id, @Param("number") String number, @Param("name") String name, @Param("teacher_id") String teacherId,
                           @Param("term_id") String termId, @Param("img") String img);

    int delCourseByTeacher(@Param("id") String id, @Param("teacher_id") String teacherId);

    int updateCourseByTeacher(@Param("id") String id,@Param("teacher_id") String teacherId, @Param("name") String name, @Param("img") String img, @Param("number") String number);

    int checkCourseAndTeacher(@Param("id") String id, @Param("teacher_id") String teacherId);

    CourseBean queryCourseInfoById(@Param("id") String id, @Param("teacher_id") String teacherId);

}
