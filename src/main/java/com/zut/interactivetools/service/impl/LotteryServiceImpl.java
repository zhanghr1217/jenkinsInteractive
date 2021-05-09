package com.zut.interactivetools.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.LotteryBean;
import com.zut.interactivetools.bean.StudentBean;
import com.zut.interactivetools.dao.LotteryDao;
import com.zut.interactivetools.exception.MyException;
import com.zut.interactivetools.service.LotteryService;
import com.zut.interactivetools.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LotteryServiceImpl implements LotteryService {

    @Autowired
    LotteryDao lotteryDao;
    @Autowired
    UtilService utilService;

    @Override
    public PageInfo<LotteryBean> queryLotteryByCourse(int pageNum, int pageSize, String teacherId, String courseId, Integer status, String studentId, String studentName, String className, String majorName, String instituteName, String createDate) {
        PageHelper.startPage(pageNum, pageSize);
        List<LotteryBean> lotteryBeans = lotteryDao.queryLotteryByCourse(teacherId, courseId, status, studentId, studentName, className, majorName, instituteName, createDate);
        return new PageInfo<>(lotteryBeans);
    }

    @Override
    public void addLotteryByCourse(String teacherId, String courseId, String studentId, Date createDate) {
        String id = UUIDUtil.getUUID();
        int i = lotteryDao.addLotteryByCourse(id, teacherId, courseId, studentId, createDate);
        if (i < 1) {
            throw new MyException(501, "addLotteryByCourse", "添加失败");
        }
    }

    @Override
    public void delLotteryByCourse(String id, String teacherId, String courseId) {
        int i = lotteryDao.delLotteryByCourse(id, teacherId, courseId);
        if (i < 1) {
            throw new MyException(502, "delLotteryByCourse", "删除失败");
        }
    }

    @Override
    public List<StudentBean> queryLotteryStudentInfoByGroup(List<String> gIds, String teacherId) {
        for (String gId : gIds) {
            utilService.checkGroup(gId,teacherId);
        }
        return lotteryDao.queryLotteryStudentInfoByGroup(gIds);
    }
}
