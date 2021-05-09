package com.zut.interactivetools.service;

import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.LotteryBean;
import com.zut.interactivetools.bean.StudentBean;

import java.util.Date;
import java.util.List;

public interface LotteryService {
    PageInfo<LotteryBean> queryLotteryByCourse(int pageNum, int pageSize, String teacherId, String courseId, Integer status,
                                               String studentId, String studentName, String className, String majorName,
                                               String instituteName, String createDate);

    void addLotteryByCourse(String teacherId, String courseId, String studentId, Date createDate);

    void delLotteryByCourse(String id, String teacherId, String courseId);

    List<StudentBean> queryLotteryStudentInfoByGroup(List<String> gIds, String teacherId);
}
