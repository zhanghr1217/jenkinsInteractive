package com.zut.interactivetools.dao;

import com.zut.interactivetools.bean.GroupStudentBean;
import com.zut.interactivetools.bean.StudentBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentDao {
    List<StudentBean> queryStudentsByGroup(@Param("group_id") String groupId, @Param("status") Integer status, @Param("student_id") String studentId,
                                           @Param("student_name") String studentName, @Param("class_name") String className,
                                           @Param("major_name") String majorName, @Param("institute_name") String instituteName);

    List<StudentBean> queryStudentByCourse(@Param("course_id") String courseId, @Param("status") Integer status, @Param("student_id") String studentId,
                                           @Param("student_name") String studentName, @Param("class_name") String className,
                                           @Param("major_name") String majorName, @Param("institute_name") String instituteName);

    List<GroupStudentBean> queryStudentIdGroupIdByCourseStu(@Param("course_id") String courseId, @Param("status") Integer status, List<String> sIds);

    GroupStudentBean queryStudentIdGroupIdByCourseStuId(@Param("course_id") String courseId, @Param("status") Integer status, @Param("student_id") String studentId);

    List<String> queryStudentsIdByGroup(@Param("group_id") String groupId, @Param("status") Integer status);

    int addStudentToGroup(@Param("group_id") String groupId, List<String> sIds, @Param("score") Double score);

    int addStudentToCourse(@Param("course_id") String courseId, @Param("student_id") String studentId);

    int addStudents(List<StudentBean> list);

    int updateStudentByGroup(@Param("group_id") String groupId, @Param("student_id") String studentId, @Param("score") Double score);

    int delStudentByCourse(@Param("course_id") String courseId, @Param("student_id") String studentId);

    int updateStudentGroupInCourseByCourse(@Param("course_id") String courseId, @Param("student_id") String studentId, @Param("group_id") String groupId);

    int recoverStudentStatusByGroup(@Param("group_id") String groupId, @Param("student_id") String studentId);

    int recoverStudentStatusByCourse(@Param("course_id") String courseId, @Param("student_id") String studentId);

    int delStudentFromGroup(@Param("group_id") String groupId, @Param("student_id") String studentId);

    StudentBean queryStudentById(@Param("student_id") String studentId);

    int addStudentById(@Param("id") String id);

    int checkStudent(@Param("id") String id);

    int checkStudentInGroup(@Param("group_id") String groupId, @Param("student_id") String studentId);

    int checkStudentInCourse(@Param("course_id") String courseId, @Param("student_id") String studentId);

    int updateStudentInfo(@Param("id") String id, @Param("name") String name, @Param("head") String head, @Param("gender") Integer gender);

    int initStudent(@Param("id") String id, @Param("name") String name, @Param("head") String head, @Param("gender") Integer gender);
}
