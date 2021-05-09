package com.zut.interactivetools.service.impl;

import com.zut.interactivetools.bean.GroupBean;
import com.zut.interactivetools.bean.GroupStudentBean;
import com.zut.interactivetools.dao.GroupDao;
import com.zut.interactivetools.dao.StudentDao;
import com.zut.interactivetools.exception.MyException;
import com.zut.interactivetools.service.GroupService;
import com.zut.interactivetools.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupDao groupDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    UtilService utilService;

    @Override
    public List<GroupBean> queryGroupByCourse(String courseId, String teacherId, Integer status) {
        return groupDao.queryGroupByCourse(courseId, teacherId, status);
    }

    @Override
    public List<GroupBean> queryGroupMapByCourse(String courseId, String teacherId, Integer status) {
        return groupDao.queryGroupMapByCourse(courseId, teacherId, status);
    }

    @Override
    public List<GroupBean> queryGroupMapByCourseNoStatus(String courseId, String teacherId) {
        return groupDao.queryGroupMapByCourseNoStatus(courseId, teacherId);
    }

    @Override
    public void addGroupByCourse(String name, String teacherId, String courseId, String termId) {
        utilService.checkCourseAndTeacher(courseId, teacherId);
        String id = UUIDUtil.getUUID();
        Date createDate = new Date();
        int i = groupDao.addGroupByCourse(id, name, teacherId, courseId, termId, createDate);
        if (i < 1) {
            throw new MyException(501, "addGroup", "添加失败");
        }
    }

    @Override
    public void updateGroupByCourse(String id, String teacherId, String courseId, String name) {
        int i = groupDao.updateGroupByCourse(id, teacherId, courseId, name);
        if (i < 1) {
            throw new MyException(503, "updateGroup", "更新失败");
        }
    }

    @Override
    public void delGroupByCourse(String id, String teacherId, String courseId) {
        int i = groupDao.delGroupByCourse(id, teacherId, courseId);
        if (i < 1) {
            throw new MyException(502, "delGroup", "删除失败");
        }
        List<String> list = studentDao.queryStudentsIdByGroup(id, 0);
        if(list.size()>0){
            List<GroupStudentBean> groupStudentBeans = studentDao.queryStudentIdGroupIdByCourseStu(courseId, 0, list);
            for (GroupStudentBean groupStudentBean : groupStudentBeans) {
                String[] split = groupStudentBean.getGid().split("\\|");
                String g = "";
                for (String s : split) {
                    if (!id.equals(s)) {
                        g += "|";
                        g += s;
                    }
                }
                if (g.length() >= 1) {
                    groupStudentBean.setGid(g.substring(1));
                }
                int i1 = studentDao.updateStudentGroupInCourseByCourse(courseId, groupStudentBean.getSid(), groupStudentBean.getGid());
                if (i1 < 1) {
                    throw new MyException(503, "updateStudentGroupInCourseByCourse", "更新失败");
                }
            }
        }


    }

    //    @Override
//    public String queryGroupNameByGroupId(String id) {
//        return groupDao.queryGroupNameByGroupId(id);
//    }
    @Override
    public void updateScore(String groupId, String studentId, Double difference, String teacherId) {
        utilService.checkGroup(groupId, teacherId);

            int i = groupDao.updateScore(groupId, studentId, difference);
            if (i < 1) {
                throw new MyException(503, "updateScore", "更新失败");
            }

    }

    @Override
    public int queryGroupSumByCourse(String groupId) {
        return groupDao.queryGroupSumByCourse(groupId);
    }
}
