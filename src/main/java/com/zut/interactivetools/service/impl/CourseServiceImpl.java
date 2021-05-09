package com.zut.interactivetools.service.impl;

import com.zut.interactivetools.bean.CourseBean;
import com.zut.interactivetools.dao.CourseDao;
import com.zut.interactivetools.exception.MyException;
import com.zut.interactivetools.service.CourseService;
import com.zut.interactivetools.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseDao courseDao;

    @Override
    public List<CourseBean> queryCourseByTeacher(String teacherId, Integer termStatus, Integer status) {
        return courseDao.queryCourseByTeacher(teacherId, termStatus, status);
    }

    @Override
    public void addCourseByTeacher(String id, String number, String name, String teacherId, String termId, String img) {
        int res = courseDao.addCourseByTeacher(id, number, name, teacherId, termId, img);
        if (res < 1) {
            throw new MyException(501, "addCourse", "创建失败");
        }
    }

    @Override
    public void delCourseByTeacher(String id, String teacherId) {
        int res = courseDao.delCourseByTeacher(id, teacherId);
        if (res < 1) {
            throw new MyException(502, "delCourse", "删除失败");
        }
    }

    @Override
    public void updateCourseByTeacher(String id, String teacherId, String name, String img, String number) {
        int res = courseDao.updateCourseByTeacher(id, teacherId, name, img, number);
        if (res < 1) {
            throw new MyException(503, "updateCourse", "更新失败");
        }
    }

    @Override
    public CourseBean queryCourseInfoById(String id, String teacherId) {
        return courseDao.queryCourseInfoById(id, teacherId);
    }
}
