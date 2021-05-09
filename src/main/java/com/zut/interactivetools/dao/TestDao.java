package com.zut.interactivetools.dao;

import com.zut.interactivetools.bean.GroupStudentBean;
import com.zut.interactivetools.bean.TestBean;
import com.zut.interactivetools.bean.TestCourseBean;
import com.zut.interactivetools.bean.TestStudentBean;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TestDao {
    List<TestBean> queryTestByCourse(@Param("teacher_id") String teacherId, @Param("course_id") String courseId, @Param("status") Integer status,
                                     @Param("test_id") String testId, @Param("test_name") String testName, @Param("score") String score,
                                     @Param("date") String date);

    int addTestByCourse(@Param("id") String id, @Param("name") String name, @Param("body") String body, @Param("score") Double score,
                        @Param("course_id") String courseId, @Param("teacher_id") String teacherId, @Param("answer") String answer,
                        @Param("create_date") Date createDate);

    int delTestByCourse(@Param("id") String id, @Param("teacher_id") String teacherId, @Param("course_id") String courseId);

    int updateTestByCourse(@Param("id") String id, @Param("teacher_id") String teacherId, @Param("course_id") String courseId,
                           @Param("name") String name, @Param("body") String body, @Param("score") Double score, @Param("answer") String answer);

    TestBean queryTestByTestId(@Param("id") String id, @Param("teacher_id") String teacherId, @Param("course_id") String courseId);

    List<TestCourseBean> queryTestCourseByCourse(@Param("teacher_id") String teacherId, @Param("course_id") String courseId,
                                                 @Param("status") Integer status, @Param("id") String id, @Param("name") String name,
                                                 @Param("date") String date,@Param("submit_status") Integer submitStatus);

    TestCourseBean queryTestCourseById(@Param("id") String id, @Param("teacher_id") String teacherId, @Param("course_id") String courseId,
                                       @Param("status") Integer status);

    TestCourseBean queryTestCourseByStudent(@Param("id") String id, @Param("course_id") String courseId, @Param("status") Integer status);

    int delTestCourseByCourse(@Param("id") String id, @Param("teacher_id") String teacherId, @Param("course_id") String courseId);

    int updateTestCourseByCourse(@Param("id") String id, @Param("teacher_id") String teacherId, @Param("course_id") String courseId,
                                 @Param("name") String name);

    int outTestCourseByCourse(@Param("id") String id, @Param("teacher_id") String teacherId, @Param("course_id") String courseId);

    int confirmTestCourseByCourse(@Param("id") String id, @Param("teacher_id") String teacherId, @Param("course_id") String courseId);

    int addTestCourseByCourse(@Param("id") String id, @Param("name") String name, @Param("create_date") Date createDate,
                              @Param("test_id") String testId, @Param("limit") Integer limit,  @Param("test_body") String testBody,@Param("test_answer") String testAnswer,
                              @Param("teacher_id") String teacherId, @Param("course_id") String courseId, @Param("group_id") String groupId, @Param("submit_status") Integer submitStatus);

    List<TestStudentBean> queryTestStudentByTest(@Param("t_id") String tId, @Param("status") Integer status);

    //    TestStudentBean queryTestStudentById(@Param("t_id") String tId, @Param("status") Integer status, @Param("student_id") String studentId,
//                                         @Param("course_score") Double courseScore);

    TestStudentBean queryTestStudentAnswerScoreByStudent(@Param("t_id") String tId, @Param("student_id") String studentId);

    Double queryTestStudentCourseScoreById(@Param("t_id") String tId, @Param("student_id") String studentId, @Param("group_id") String groupId);

    List<String> queryTestStudentAnswerByTeacherTest(@Param("t_id") String tId);

    int updateTestStudentByCourse(@Param("t_id") String tId, @Param("student_id") String studentId, @Param("group_id") String groupId,
                                  @Param("course_score") Double courseScore);

    int delTestStudentByCourse(@Param("t_id") String tId, @Param("student_id") String studentId, @Param("group_id") String groupId);

    int addTestStudentByCourse(@Param("t_id") String tId, List<GroupStudentBean> list);

    int updateTestStudentByStu(@Param("t_id") String tId, @Param("student_id") String studentId, @Param("sub_date") Date subDate,
                               @Param("score") Double score, @Param("answer") String answer);

    int checkTestCourse(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId);

    int checkTestStudentByStudent(@Param("t_id") String tId, @Param("student_id") String studentId);

    int checkTestStudentIsAnswerByStudent(@Param("t_id") String tId, @Param("student_id") String studentId);

    int checkTestCourseAlive(@Param("id") String id);

    int checkTest(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId);

    int updateSubmitStatus(@Param("id") String id,@Param("course_id") String courseId,@Param("teacher_id") String teacherId);

}
