package com.zut.interactivetools.service.impl;

import com.zut.interactivetools.dao.*;
import com.zut.interactivetools.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class  UtilService {

    @Autowired
    CourseDao courseDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    GroupDao groupDao;
    @Autowired
    TestDao testDao;
    @Autowired
    DiscussDao discussDao;
    @Autowired
    SignDao signDao;
    @Autowired
    UserDao userDao;
    @Autowired
    LibraryDao libraryDao;

    public int checkTeacher(String id){
        int i = userDao.checkTeacher(id);
        return i;
    }

    public void checkCourseAndTeacher(String courseId, String teacherId) {
        int i = courseDao.checkCourseAndTeacher(courseId, teacherId);
        if (i < 1) {
            throw new MyException(504, "checkCourseAndTeacher", "检测错误");
        }
    }

    public void addNotExistStudent(String studentId) {
        int i = studentDao.checkStudent(studentId);
        if (i < 1) {
            int i1 = studentDao.addStudentById(studentId);
            if (i1 < 1) {
                throw new MyException(501, "id:"+studentId, "添加失败");
            }
        }
    }

    public void checkGroup(String groupId, String teacherId) {
        int i = groupDao.checkGroup(groupId, teacherId);
        if (i < 1) {
            throw new MyException(504, "checkGroup", "检测错误");
        }
    }

    public int checkStudent(String id) {
        return studentDao.checkStudent(id);
    }

    public void checkTest(String id, String courseId, String teacherId){
        int i = testDao.checkTest(id, courseId, teacherId);
        if (i < 1) {
            throw new MyException(504, "checkTest", "检测错误");
        }
    }

    public void checkTestCourse(String id, String courseId, String teacherId) {
        int i = testDao.checkTestCourse(id, courseId, teacherId);
        if (i < 1) {
            throw new MyException(504, "checkTestCourse", "检测错误");
        }
    }

    public void checkGroupStudent(String groupId, String studentId){
        int i = groupDao.checkGroupStudent(groupId, studentId);
        if (i < 1) {
            throw new MyException(504, "studentId:"+studentId, "检测错误");
        }
    }

    public void checkTestStudentByStudent(String tId,  String studentId){
        int i = testDao.checkTestStudentByStudent(tId, studentId);
        if (i < 1) {
            throw new MyException(504, "checkTestStudentByStudent", "检测错误");
        }
    }

    public void checkTestStudentIsAnswerByStudent(String tId,  String studentId){
        int i = testDao.checkTestStudentIsAnswerByStudent(tId, studentId);
        if (i < 1) {
            throw new MyException(504, "checkTestStudentIsAnswerByStudent", "已经作答");
        }
    }

    public void checkTestCourseAlive(String id){
        int i = testDao.checkTestCourseAlive(id);
        if (i < 1) {
            throw new MyException(504, "checkTestCourseAlive", "测试已经结束");
        }
    }

    public void checkDiscussCourse(String id, String courseId, String teacherId){
        int i = discussDao.checkDiscussCourse(id, courseId, teacherId);
        if (i < 1) {
            throw new MyException(504, "checkDiscussCourse", "检测错误");
        }
    }

    public void checkDiscussCourseAlive(String id){
        int i = discussDao.checkDiscussCourseAlive(id);
        if (i < 1) {
            throw new MyException(504, "checkDiscussCourseAlive", "讨论已经结束");
        }
    }

    public void checkSignAliveById(String id){
        int i = signDao.querySignAliveById(id);
        if (i < 1) {
            throw new MyException(504, "checkSignAliveById", "签到已经结束");
        }
    }

    public void checkAliveQRCodeById(String id, String signId){
        int i = signDao.queryAliveQRCodeById(id, signId);
        if (i < 1) {
            throw new MyException(504, "checkAliveQRCodeById", "二维码过期");
        }
    }

    public void checkSignTeacher(String id, String courseId, String teacherId){
        int i = signDao.checkSignTeacher(id, courseId, teacherId);
        if (i < 1) {
            throw new MyException(504, "checkSignTeacher", "检测错误");
        }
    }

    public void checkSignTeacherAndAlive(String id, String courseId, String teacherId, Integer type){
        int i = signDao.checkSignTeacherAndAlive(id, courseId, teacherId, type);
        if (i < 1) {
            throw new MyException(504, "checkSignTeacherAndAlive", "签到已经结束");
        }
    }

    public int checkStudentInGroup(String groupId, String studentId){
        int i = studentDao.checkStudentInGroup(groupId, studentId);
        return i;
    }

    public int checkStudentInCourse(String courseId, String studentId){
        int i = studentDao.checkStudentInCourse(courseId, studentId);
        return i;
    }

    public int checkSignLocation(String signId){
        int i = signDao.checkSignLocation(signId);
        return i;
    }

    public void checkDiscussStudent(String discussId, String studentId){
        int i = discussDao.checkDiscussStudent(discussId, studentId);
        if (i < 1) {
            throw new MyException(504, "checkDiscussStudent", "检测错误");
        }
    }

    public void checkLibrary(String libraryId,String teacherId){
        int i = libraryDao.checkLibrary(libraryId, teacherId);
        if (i < 1) {
            throw new MyException(504, "checkLibrary", "检测错误");
        }
    }

    public void checkSection(String sectionId, String libraryId){
        int i = libraryDao.checkSection(sectionId, libraryId);
        if (i < 1) {
            throw new MyException(504, "checkSection", "检测错误");
        }
    }

}
