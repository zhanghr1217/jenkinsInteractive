package com.zut.interactivetools.service;

import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.GroupStudentBean;
import com.zut.interactivetools.bean.StudentBean;

import java.util.List;

public interface StudentService {
    List<StudentBean> queryStudentsByGroup(String groupId, Integer status, String studentId, String studentName,
                                           String className, String majorName, String instituteName, String teacherId);

    List<StudentBean> queryStudentsByCourse(String courseId, Integer status, String studentId,
                                               String studentName, String className, String majorName, String instituteName,String teacherId);

//    List<GroupStudentBean> queryStudentIdGroupIdByCourseStu(String courseId, Integer status);

    List<String> queryStudentsIdByGroup(String groupId,String teacherId , Integer status);

    GroupStudentBean queryStudentIdGroupIdByCourseStuId(String courseId, Integer status, String studentId);

    void addStudentToGroup(String groupId, List<String> sIds, Double score, String teacherId);

    void addStudentToCourse(String courseId, List<String> sIds, String teacherId);

    void updateStudentGroupInCourseByCourse(String courseId, List<String> sids, String groupId);

    void delStudentByCourse(String courseId, String studentId, String teacherId);

    void updateStudentByGroup(String groupId, String studentId, Double score, String teacherId);

    void delStudentFromGroup(String groupId, String studentId, String teacherId);

    StudentBean queryStudentById(String studentId);

    void addStudents(List<StudentBean> students);

    void updateStudentInfo(String id, String name, String head, Integer gender);

    void updateStudentGroupInCourseByCourse(String courseId, String studentId, String groupId);

}
