package com.zut.interactivetools.dao;

import com.zut.interactivetools.bean.SubmitTaskBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SignCacheDao {

    Integer addSignCache(@Param("id") String id, @Param("taskTimestamp") long taskTimestamp, @Param("taskEnum") Enum taskEnum,
                         @Param("needSendUser") String needSendUser, @Param("teacherId") String teacherId, @Param("courseId") String courseId,
                         @Param("courseNum") String courseNum, @Param("teacherName") String teacherName, @Param("courseName") String courseName);

    List<SubmitTaskBean> getSignCachesByTeacher(@Param("teacherId") String teacherId, @Param("courseId") String courseId);

    SubmitTaskBean getSignCacheById(@Param("id") String id, @Param("teacherId") String teacherId, @Param("courseId") String courseId);

    Integer delCache(@Param("id") String id, @Param("teacherId") String teacherId, @Param("courseId") String courseId);
}
