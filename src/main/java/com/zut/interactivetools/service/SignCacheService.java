package com.zut.interactivetools.service;


import com.zut.interactivetools.bean.SubmitTaskBean;

import java.util.List;

public interface SignCacheService {
    void addSignCache(String id, long taskTimestamp, Enum taskEnum, String needSendUser, String teacherId, String courseId,
                      String courseNum, String teacherName, String courseName);

    List<SubmitTaskBean> getSignCachesByTeacher(String teacherId, String courseId);

    SubmitTaskBean getSignCacheById(String id, String teacherId, String courseId);

    void delCache(String id, String teacherId, String courseId);
}
