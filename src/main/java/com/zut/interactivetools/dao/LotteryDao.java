package com.zut.interactivetools.dao;

import com.zut.interactivetools.bean.LotteryBean;
import com.zut.interactivetools.bean.StudentBean;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface LotteryDao {

    List<LotteryBean> queryLotteryByCourse(@Param("teacher_id") String teacherId, @Param("course_id") String courseId,
                                           @Param("status") Integer status, @Param("student_id") String studentId,
                                           @Param("student_name") String studentName, @Param("class_name") String className,
                                           @Param("major_name") String majorName, @Param("institute_name") String instituteName,
                                           @Param("create_date") String createDate);

    int addLotteryByCourse(@Param("id") String id, @Param("teacher_id") String teacherId, @Param("course_id") String courseId,
                           @Param("student_id") String studentId, @Param("create_date") Date createDate);

    int delLotteryByCourse(@Param("id") String id, @Param("teacher_id") String teacherId, @Param("course_id") String courseId);

    List<StudentBean> queryLotteryStudentInfoByGroup(List<String> gIds);
}
