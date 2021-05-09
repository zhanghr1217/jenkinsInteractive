package com.zut.interactivetools.dao;

import com.zut.interactivetools.bean.GroupBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

public interface GroupDao {
    List<GroupBean> queryGroupByCourse(@Param("course_id") String courseId, @Param("teacher_id") String teacherId, @Param("status") Integer status);

    List<GroupBean> queryGroupMapByCourse(@Param("course_id") String courseId, @Param("teacher_id") String teacherId, @Param("status") Integer status);

    List<GroupBean> queryGroupMapByCourseNoStatus(@Param("course_id") String courseId, @Param("teacher_id") String teacherId);

    int addGroupByCourse(@Param("id") String id, @Param("name") String name, @Param("teacher_id") String teacherId, @Param("course_id") String courseId,
                         @Param("term_id") String termId, @Param("create_date")Date createDate);

    int updateGroupByCourse(@Param("id") String id, @Param("teacher_id") String teacherId, @Param("course_id") String courseId, @Param("name") String name);

    int delGroupByCourse(@Param("id") String id, @Param("teacher_id") String teacherId, @Param("course_id") String courseId);

    String queryGroupNameByGroupId(@Param("id") String id);

    int updateScore(@Param("group_id") String groupId, @Param("student_id") String studentId, @Param("difference") Double difference);

    int checkGroup(@Param("id") String id, @Param("teacher_id") String teacherId);

    int checkGroupStudent(@Param("group_id") String groupId, @Param("student_id") String studentId);

    int queryGroupSumByCourse(@Param("group_id") String groupId);
}
