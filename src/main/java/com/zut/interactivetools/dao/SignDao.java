package com.zut.interactivetools.dao;

import com.zut.interactivetools.bean.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SignDao {
    List<SignBean> querySignByCourse(@Param("course_id") String courseId, @Param("teacher_id") String teacherId, @Param("status") Integer status,
                                     @Param("id") String id, @Param("name") String name, @Param("type") Integer type, @Param("begin") String begin,
                                     @Param("score") Double score, @Param("submit_status") Integer submitStatus);

    int delSignByCourse(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId);

    int updateSignByCourse(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId,
                           @Param("name") String name);

    int addSignByCourse(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId,
                        @Param("name") String name, @Param("begin") Date begin, @Param("limit") Integer limit, @Param("type") Integer type,
                        @Param("score") Double score, @Param("group_id") String groupId, @Param("submit_status") Integer submitStatus);

    int addQRCode(@Param("id") String id, @Param("token") String token, @Param("date") Date date, @Param("sign_id") String signId);

    int killSign(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId);

    int querySignAliveById(@Param("id") String id);

    int queryAliveQRCodeById(@Param("id") String id, @Param("sign_id") String signId);

    double querySignScoreById(@Param("id") String id);

    int killQRCode(@Param("sign_id") String signId);

    List<SignRecordBean> querySignRecordByCourse(@Param("sign_id") String signId, @Param("status") Integer status, @Param("student_id") String studentId,
                                                 @Param("student_name") String studentName, @Param("class_name") String className,
                                                 @Param("major_name") String majorName, @Param("confirm") Integer confirm);

    Double queryScore(@Param("sign_id") String signId, @Param("student_id") String studentId, @Param("group_id") String groupId);

    int delSignRecordByCourse(@Param("sign_id") String signId, @Param("student_id") String studentId, @Param("group_id") String groupId);

    int updateSignRecordByCourse(@Param("sign_id") String signId, @Param("student_id") String studentId, @Param("group_id") String groupId,
                                 @Param("record") Integer record, @Param("score") Double score);

    int confirmSignRecordByCourse(@Param("sign_id") String signId, @Param("student_id") String studentId, @Param("group_id") String groupId,
                                  @Param("record") Integer record, @Param("score") Double score);

    int addSignRecordByCourse(@Param("sign_id") String signId, @Param("type") Integer type, List<GroupStudentBean> list);

    int updateSignRecordByStudent(@Param("sign_id") String signId, @Param("student_id") String studentId, @Param("sign_date") Date sign_date,
                                  @Param("extra") String extra, @Param("record") Integer record, @Param("score") Double score);

    int checkSignTeacher(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId);

    int checkSignTeacherAndAlive(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId, @Param("type") Integer type);

    List<StudentBean> querySignStudentInfoById(@Param("sign_id") String signId, @Param("record") Integer record);

    List<StudentBean> querySignStudentDetailInfoById(@Param("sign_id") String signId, @Param("record") Integer record);

    int addSignLocation(@Param("sign_id") String signId, @Param("location") String location, @Param("longitude") Double longitude,
                        @Param("latitude") Double latitude, @Param("info") String info);

    int checkSignLocation(@Param("sign_id") String signId);

    int updateSignLocation(@Param("sign_id") String signId, @Param("location") String location, @Param("longitude") Double longitude,
                           @Param("latitude") Double latitude, @Param("info") String info);

    SignLocationBean getSignLocation(@Param("sign_id") String signId);

    int confirmSign(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId);

    List<SignBean> querySignCacheByCourse(@Param("course_id") String courseId, @Param("teacher_id") String teacherId, @Param("status") Integer status,
                                          @Param("id") String id, @Param("name") String name, @Param("type") Integer type, @Param("score") Double score);

    int updateSubmitStatus(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId);

    List<SignBean> querySignByCensus(@Param("course_id") String courseId, @Param("teacher_id") String teacherId, @Param("status") Integer status, @Param("censusType") Integer censusType);

    List<StudentBean> querySignStudentByCensus(List<SignBean> list);

    List<SignBean> querySignCountByCensus(List<SignBean> list);
}
