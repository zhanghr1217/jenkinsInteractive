package com.zut.interactivetools.service;

import com.zut.interactivetools.bean.GroupBean;

import java.util.Date;
import java.util.List;

public interface GroupService {
    List<GroupBean> queryGroupByCourse(String courseId, String teacherId, Integer status);

    List<GroupBean> queryGroupMapByCourse(String courseId, String teacherId, Integer status);

    List<GroupBean> queryGroupMapByCourseNoStatus(String courseId, String teacherId);

    void addGroupByCourse(String name, String teacherId, String courseId, String termId);

    void updateGroupByCourse(String id, String teacherId, String courseId, String name);

    void delGroupByCourse(String id, String teacherId, String courseId);

//    String queryGroupNameByGroupId(String id);

    void updateScore(String groupId, String studentId, Double difference, String teacherId);

    int queryGroupSumByCourse(String groupId);
}
