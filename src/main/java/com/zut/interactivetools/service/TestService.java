package com.zut.interactivetools.service;

import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.GroupStudentBean;
import com.zut.interactivetools.bean.TestBean;
import com.zut.interactivetools.bean.TestCourseBean;
import com.zut.interactivetools.bean.TestStudentBean;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TestService {
    PageInfo<TestBean> queryTestByCourse(int pageNum, int pageSize, String teacherId, String courseId, Integer status, String testId,
                                         String testName, String score, String date);

    void addTestByCourse(String name, String body, Double score, String courseId, String teacherId, String answer);

    void delTestByCourse(String id, String teacherId, String courseId);

    void updateTestByCourse(String id, String teacherId, String courseId, String name, String body, Double score, String answer);

    TestBean queryTestByTestId(String id, String teacherId, String courseId);

    PageInfo<TestCourseBean> queryTestCourseByCourse(int pageNum, int pageSize, String teacherId, String courseId, Integer status,
                                                     String id, String name, String date, Integer submitStatus);

    TestCourseBean queryTestCourseById(String id, String teacherId, String courseId, Integer status);

    TestCourseBean queryTestCourseByStudent(String id, String courseId, Integer status, String studentId);

    void delTestCourseByCourse(String id, String teacherId, String courseId);

    void updateTestCourseByCourse(String id, String teacherId, String courseId, String name);

    void outTestCourseByCourse(String id, String teacherId, String courseId);

    void confirmTestCourseByCourse(String id, String teacherId, String courseId);

    void addTestCourseByCourse(String id, String name, Date createDate, String testId, Integer limit, String testBody,String testAnswer, String teacherId, String courseId,
                              String groupId,Integer submitStatus);

    List<TestStudentBean> queryTestStudentByTest(String tId, Integer status, String teacherId, String courseId);

//    TestStudentBean queryTestStudentById(String tId, Integer status, String studentId, Double courseScore);

    TestStudentBean queryTestStudentAnswerScoreByStudent(String tId, String studentId);

    List<Map<String, Integer>> queryTestStudentAnswerByTeacherTest(String tId, String courseId, String teacherId);

    void updateTestStudentByCourse(String tId, String studentId, String groupId, Double courseScore, String teacherId,
                                   String courseId);

    void delTestStudentByCourse(String tId, String studentId, String groupId, String teacherId, String courseId);

    void addTestStudentByCourse(String tId, List<GroupStudentBean> list, String teacherId, String courseId);

    void updateTestStudentByStu(String tId, String studentId, String courseId, Date subDate,  Object answer);

    int checkTestCourseAlive(String id);

    int checkTestStudentIsAnswerByStudent(String tId, String studentId);

    void updateSubmitStatus(String id, String courseId, String teacherId);

}
