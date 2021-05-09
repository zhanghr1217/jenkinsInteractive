package com.zut.interactivetools.service.impl;

import com.zut.interactivetools.bean.SubmitTaskBean;
import com.zut.interactivetools.dao.SignCacheDao;
import com.zut.interactivetools.exception.MyException;
import com.zut.interactivetools.service.SignCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: interactivetools_integration
 * @description: 签到缓存
 * @author: zjj
 * @create: 2021-02-06 15:36
 **/


@Service
public class SignCacheServiceImpl implements SignCacheService {

    @Autowired
    SignCacheDao signCacheDao;


    @Override
    public void addSignCache(String id, long taskTimestamp, Enum taskEnum, String needSendUser, String teacherId, String courseId,
                             String courseNum, String teacherName, String courseName) {

        Integer integer = signCacheDao.addSignCache(id, taskTimestamp, taskEnum, needSendUser, teacherId, courseId,courseNum, teacherName,courseName);
        if(integer < 1){

            throw new MyException(501, "addSignCache", "添加失败");
        }
    }

    @Override
    public List<SubmitTaskBean> getSignCachesByTeacher(String teacherId, String courseId) {
        return signCacheDao.getSignCachesByTeacher(teacherId, courseId);
    }

    @Override
    public SubmitTaskBean getSignCacheById(String id, String teacherId, String courseId) {
        return signCacheDao.getSignCacheById(id, teacherId, courseId);
    }

    @Override
    public void delCache(String id, String teacherId, String courseId) {
        Integer integer = signCacheDao.delCache(id, teacherId, courseId);
        if(integer<1){
            throw new MyException(502, "delCache", "删除失败");
        }
    }
}
