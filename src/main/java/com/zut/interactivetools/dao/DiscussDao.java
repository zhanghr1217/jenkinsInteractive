package com.zut.interactivetools.dao;

import com.zut.interactivetools.bean.DiscussBean;
import com.zut.interactivetools.bean.DiscussCourseBean;
import com.zut.interactivetools.bean.DiscussStudentBean;
import com.zut.interactivetools.bean.GroupStudentBean;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DiscussDao {
    List<DiscussBean> queryDiscussByCourse(@Param("course_id") String courseId, @Param("teacher_id") String teacherId, @Param("status") Integer status,
                                           @Param("discuss_id") String discussId, @Param("discuss_name") String discussName,
                                           @Param("theme") String theme, @Param("date") Date date, @Param("score") Double score);

    DiscussBean queryDiscussById(@Param("course_id") String courseId, @Param("teacher_id") String teacherId, @Param("id") String id);

    int delDiscussByCourse(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId);

    int updateDiscussByCourse(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId,
                              @Param("name") String name, @Param("theme") String theme, @Param("score") Double score);

    int addDiscussByCourse(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId,
                           @Param("name") String name, @Param("theme") String theme, @Param("date") Date date, @Param("score") Double score);

    List<DiscussCourseBean> queryDiscussCourseByCourse(@Param("course_id") String courseId, @Param("teacher_id") String teacherId,
                                                       @Param("status") Integer status, @Param("id") String id, @Param("name") String name,
                                                       @Param("date") String date);

    DiscussCourseBean queryDiscussCourseThemeByIdTeacher(@Param("id") String id, @Param("teacher_id") String teacherId);

    DiscussCourseBean queryDiscussCourseThemeByIdStudent(@Param("id") String id);

    int addDiscussCourseByCourse(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId,
                                 @Param("name") String name, @Param("discuss_body") String discussBody, @Param("create_date") Date date,
                                 @Param("group_id") String groupId);

    int delDiscussCourseByCourse(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId);

    int updateDiscussCourseByCourse(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId,
                                    @Param("name") String name);

    int outDiscussCourseByCourse(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId);

    List<DiscussStudentBean> queryDiscussStudentByCourse(@Param("discuss_id") String discussId, @Param("status") Integer status);

    List<DiscussStudentBean> queryDiscussStudentInfoByDiscuss(@Param("discuss_id") String discussId, @Param("status") Integer status);

    List<String> queryDiscussStudentBodyByStudent(@Param("discuss_id") String discussId, @Param("status") Integer status, @Param("student_id") String studentId);

    Double queryScoreById(@Param("discuss_id") String discussId, @Param("student_id") String studentId, @Param("group_id") String groupId);

    int addDiscussStudentByCourse(@Param("discuss_id") String discussId, List<GroupStudentBean> list);

    int delDiscussStudentByCourse(@Param("discuss_id") String discussId, @Param("student_id") String studentId, @Param("group_id") String groupId);


    int updateDiscussStudentByCourse(@Param("discuss_id") String discussId, @Param("student_id") String studentId, @Param("group_id") String groupId,
                                     @Param("score") Double score);

    int updateDiscussStudentByStudent(@Param("discuss_id") String discussId, @Param("student_id") String studentId, @Param("body") String body,
                                      @Param("date") Date date);

    int confirmDiscussStudentByCourse(@Param("score") Double score, @Param("discuss_id") String discussId,
                                      @Param("student_id") String studentId, @Param("group_id") String groupId);

    int checkDiscussCourse(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId);

    int checkDiscussCourseAlive(@Param("id") String id);

    int checkDiscussStudent(@Param("discuss_id") String discussId, @Param("student_id") String studentId);

    int confirmDiscuss(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId);
}
