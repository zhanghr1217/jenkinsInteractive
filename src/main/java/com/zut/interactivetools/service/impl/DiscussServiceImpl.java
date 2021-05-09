package com.zut.interactivetools.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.*;
import com.zut.interactivetools.dao.DiscussDao;
import com.zut.interactivetools.exception.MyException;
import com.zut.interactivetools.service.DiscussService;
import com.zut.interactivetools.service.GroupService;
import com.zut.interactivetools.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Service
public class DiscussServiceImpl implements DiscussService {

    @Autowired
    DiscussDao discussDao;
    @Autowired
    UtilService utilService;
    @Autowired
    GroupService groupService;

    @Override
    public PageInfo<DiscussBean> queryDiscussByCourse(int pageNum, int pageSize, String courseId, String teacherId, Integer status,
                                                      String discussId, String discussName, String theme, Date date, Double score) {
        PageHelper.startPage(pageNum, pageSize);
        List<DiscussBean> discussBeans = discussDao.queryDiscussByCourse(courseId, teacherId, status, discussId, discussName, theme, date, score);
        return new PageInfo<>(discussBeans);
    }

    @Override
    public DiscussBean queryDiscussById(String courseId, String teacherId, String id) {
        return discussDao.queryDiscussById(courseId, teacherId, id);
    }

    @Override
    public void delDiscussByCourse(String id, String courseId, String teacherId) {
        int i = discussDao.delDiscussByCourse(id, courseId, teacherId);
        if (i < 1) {
            throw new MyException(502, "delDiscussByCourse", "删除失败");
        }
    }

    @Override
    public void updateDiscussByCourse(String id, String courseId, String teacherId, String name, String theme, Double score) {
        int i = discussDao.updateDiscussByCourse(id, courseId, teacherId, name, theme, score);
        if (i < 1) {
            throw new MyException(503, "updateDiscussByCourse", "更新失败");
        }
    }

    @Override
    public void addDiscussByCourse(String courseId, String teacherId, String name, String theme, Date date, Double score) {
        utilService.checkCourseAndTeacher(courseId, teacherId);
        String id = UUIDUtil.getUUID();
        int i = discussDao.addDiscussByCourse(id, courseId, teacherId, name, theme, date, score);
        if (i < 1) {
            throw new MyException(501, "addDiscussByCourse", "添加失败");
        }
    }

    @Override
    public PageInfo<DiscussCourseBean> queryDiscussCourseByCourse(int pageNum, int pageSize, String courseId, String teacherId,
                                                                  Integer status, String id, String name, String date) {
        PageHelper.startPage(pageNum, pageSize);
        List<DiscussCourseBean> discussCourseBeans = discussDao.queryDiscussCourseByCourse(courseId, teacherId, status, id, name, date);
        List<GroupBean> groupBeans = groupService.queryGroupMapByCourseNoStatus(courseId, teacherId);
        HashMap<String, String> map = new HashMap<>();
        for (GroupBean groupBean : groupBeans) {
            map.put(groupBean.getId(),groupBean.getName());
        }
        for (DiscussCourseBean discussCourseBean : discussCourseBeans) {
            if (discussCourseBean.getGroupId() != null && !"".equals(discussCourseBean.getGroupId())) {
                String[] split = discussCourseBean.getGroupId().split("\\|");
                List<String> gname = new ArrayList<>();
                String g = null;
                for (String s : split) {
                    if ((g = map.get(s)) != null) {
                        gname.add(g);
                    }
                }
                discussCourseBean.setGroupName(gname);
            } else {
                discussCourseBean.setGroupName(new ArrayList<>());
            }
        }
        return new PageInfo<>(discussCourseBeans);
    }

    @Override
    public DiscussCourseBean queryDiscussCourseThemeByIdTeacher(String id, String teacherId) {
        DiscussCourseBean discussCourseBean = discussDao.queryDiscussCourseThemeByIdTeacher(id, teacherId);
        return discussCourseBean;
    }

    @Override
    public DiscussCourseBean queryDiscussCourseThemeByIdStudent(String id) {
        return discussDao.queryDiscussCourseThemeByIdStudent(id);
    }

    @Override
    public void addDiscussCourseByCourse(String id, String courseId, String teacherId, String name,String discussBody, Date date, String groupId) {
        utilService.checkCourseAndTeacher(courseId,teacherId);
        int i = discussDao.addDiscussCourseByCourse(id, courseId, teacherId, name, discussBody, date, groupId);
        if (i < 1) {
            throw new MyException(501, "addDiscussCourseByCourse", "添加失败");
        }
    }

    @Override
    public void delDiscussCourseByCourse(String id, String courseId, String teacherId) {
        int i = discussDao.delDiscussCourseByCourse(id, courseId, teacherId);
        if (i < 1) {
            throw new MyException(502, "delDiscussByCourse", "删除失败");
        }
    }

    @Override
    public void updateDiscussCourseByCourse(String id, String courseId, String teacherId, String name) {
        int i = discussDao.updateDiscussCourseByCourse(id, courseId, teacherId, name);
        if (i < 1) {
            throw new MyException(503, "updateDiscussByCourse", "更新失败");
        }
    }

    @Override
    public void outDiscussCourseByCourse(String id, String courseId, String teacherId) {
        int i = discussDao.outDiscussCourseByCourse(id, courseId, teacherId);
        if (i < 1) {
            throw new MyException(503, "outDiscussCourseByCourse", "更新失败");
        }
    }

    @Override
    public List<DiscussStudentBean> queryDiscussStudentByCourse(String discussId, Integer status, String teacherId, String courseId) {
        utilService.checkDiscussCourse(discussId,courseId,teacherId);
        return discussDao.queryDiscussStudentByCourse(discussId, status);
    }

    @Override
    public List<DiscussStudentBean> queryDiscussStudentInfoByDiscuss(String discussId, Integer status, String teacherId, String courseId) {
        utilService.checkDiscussCourse(discussId,courseId,teacherId);
        return discussDao.queryDiscussStudentInfoByDiscuss(discussId, status);
    }

    @Override
    public List<String> queryDiscussStudentBodyByStudent(String discussId, Integer status, String studentId) {
        return discussDao.queryDiscussStudentBodyByStudent(discussId, status, studentId);
    }

    @Override
    public void addDiscussStudentByCourse(String discussId, List<GroupStudentBean> list, String teacherId, String courseId) {
        utilService.checkDiscussCourse(discussId,courseId,teacherId);
        for (GroupStudentBean groupStudentBean : list) {
            utilService.checkGroupStudent(groupStudentBean.getGid(),groupStudentBean.getSid());
        }
        int i = discussDao.addDiscussStudentByCourse(discussId, list);
        if (i < 1) {
            throw new MyException(501, "addDiscussStudentByCourse", "添加失败");
        }
    }

    @Override
    public void delDiscussStudentByCourse(String discussId, String studentId, String groupId, String teacherId, String courseId) {
        utilService.checkDiscussCourse(discussId,courseId,teacherId);
        int i = discussDao.delDiscussStudentByCourse(discussId, studentId, groupId);
        if (i < 1) {
            throw new MyException(502, "delDiscussStudentByCourse", "删除失败");
        }
    }

    @Override
    public void updateDiscussStudentByCourse(String discussId, String studentId, String groupId, Double score, String teacherId, String courseId) {
        utilService.checkDiscussCourse(discussId,courseId,teacherId);
        int i = discussDao.updateDiscussStudentByCourse(discussId, studentId, groupId, score);
        if (i < 1) {
            throw new MyException(503, "updateDiscussStudentByCourse", "更新失败");
        }
    }

    @Override
    public void updateDiscussStudentByStudent(String discussId, String studentId, String body) {
        int i = discussDao.updateDiscussStudentByStudent(discussId, studentId, body, new Date());
        if (i < 1) {
            throw new MyException(503, "updateDiscussStudentByStudent", "更新失败");
        }
    }

    @Override
    public void confirmDiscussStudentByCourse(Double score, String discussId, String studentId, String groupId) {
        int i = discussDao.confirmDiscussStudentByCourse(score, discussId, studentId, groupId);
        if (i < 1) {
            throw new MyException(503, "confirmDiscussStudentByCourse", "更新失败");
        }
    }

    @Override
    public void confirmDiscuss(String id, String courseId, String teacherId) {
        int i = discussDao.confirmDiscuss(id, courseId, teacherId);
        if (i < 1) {
            throw new MyException(503, "confirmDiscussStudentByCourse", "更新失败");
        }
    }
}
