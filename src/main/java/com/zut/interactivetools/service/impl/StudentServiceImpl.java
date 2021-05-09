package com.zut.interactivetools.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.GroupStudentBean;
import com.zut.interactivetools.bean.StudentBean;
import com.zut.interactivetools.dao.StudentDao;
import com.zut.interactivetools.exception.MyException;
import com.zut.interactivetools.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentDao studentDao;
    @Autowired
    UtilService utilService;

    @Override
    public List<StudentBean> queryStudentsByGroup(String groupId, Integer status, String studentId,
                                                      String studentName, String className, String majorName, String instituteName, String teacherId) {
        utilService.checkGroup(groupId, teacherId);
        List<StudentBean> studentBeans = studentDao.queryStudentsByGroup(groupId, status, studentId, studentName, className, majorName, instituteName);
        return studentBeans;
    }

    @Override
    public List<StudentBean> queryStudentsByCourse(String courseId, Integer status, String studentId, String studentName,
                                                   String className, String majorName, String instituteName, String teacherId) {
        utilService.checkCourseAndTeacher(courseId, teacherId);
        List<StudentBean> studentBeans = studentDao.queryStudentByCourse(courseId, status, studentId, studentName, className, majorName, instituteName);
        return studentBeans;
    }

    @Override
    public List<String> queryStudentsIdByGroup(String groupId, String teacherId, Integer status) {
        utilService.checkGroup(groupId,teacherId);
        List<String> list = studentDao.queryStudentsIdByGroup(groupId, status);
        return list;
    }

    @Override
    public GroupStudentBean queryStudentIdGroupIdByCourseStuId(String courseId, Integer status, String studentId) {
        return studentDao.queryStudentIdGroupIdByCourseStuId(courseId, status, studentId);
    }

    @Override
    public void addStudentToGroup(String groupId, List<String> sIds, Double score, String teacherId) {
        utilService.checkGroup(groupId, teacherId);
        List<String> s = new ArrayList<>();
        for (int i1 = 0; i1 < sIds.size(); i1++) {
            int i = utilService.checkStudentInGroup(groupId, sIds.get(i1));
            if (i > 0) {
                int res = studentDao.recoverStudentStatusByGroup(groupId, sIds.get(i1));
                if (res < 1) {
                    throw new MyException(503, "recoverStudentStatusByGroup", "更新失败");
                }
                s.add(sIds.get(i1));
            }
        }
        for (String ss : s) {
            sIds.remove(ss);
        }
        if (sIds.size() > 0) {
            int i = studentDao.addStudentToGroup(groupId, sIds, score);
            if (i < 1) {
                throw new MyException(501, "addStudentToGroup", "添加失败");
            }
        }


    }

    @Override
    public void addStudentToCourse(String courseId, List<String> sIds, String teacherId) {
        utilService.checkCourseAndTeacher(courseId, teacherId);
        int index = 0;
        for (String sId : sIds) {
            index++;
            utilService.addNotExistStudent(sId);
            int i = utilService.checkStudentInCourse(courseId, sId);
            if(i<1){
                int i1 = studentDao.addStudentToCourse(courseId, sId);
                if (i1 < 1) {
                    throw new MyException(501, "第"+index+"行:"+sId, "添加失败");
                }
            }else {
                int i1 = studentDao.recoverStudentStatusByCourse(courseId, sId);
                if (i1 < 1) {
                    throw new MyException(503, "第"+index+"行:"+sId, "更新失败");
                }
            }
        }

    }

    @Override
    public void updateStudentGroupInCourseByCourse(String courseId, List<String> sids, String groupId) {
        List<GroupStudentBean> groupStudentBeans = studentDao.queryStudentIdGroupIdByCourseStu(courseId, 0, sids);
        for (GroupStudentBean groupStudentBean : groupStudentBeans) {
            if (groupStudentBean.getGid() != null && !"".equals(groupStudentBean.getGid())) {
                String fields[] = groupStudentBean.getGid().split("\\|");
                boolean flag = false;
                for (String field : fields) {
                    if (groupId.equals(field)) {
                        flag = true;
                    }
                }
                if (flag) {
                    int i = studentDao.updateStudentGroupInCourseByCourse(courseId, groupStudentBean.getSid(), groupStudentBean.getGid());
                    if (i < 1) {
                        throw new MyException(503, "updateStudentGroupInCourseByCourse", "更新失败");
                    }
                } else {
                    groupStudentBean.setGid(groupStudentBean.getGid() + "|" + groupId);
                    int i = studentDao.updateStudentGroupInCourseByCourse(courseId, groupStudentBean.getSid(), groupStudentBean.getGid());
                    if (i < 1) {
                        throw new MyException(503, "updateStudentGroupInCourseByCourse", "更新失败");
                    }
                }
            } else {
                groupStudentBean.setGid(groupId);
                int i = studentDao.updateStudentGroupInCourseByCourse(courseId, groupStudentBean.getSid(), groupStudentBean.getGid());
                if (i < 1) {
                    throw new MyException(503, "updateStudentGroupInCourseByCourse", "更新失败");
                }
            }
        }
    }

    @Override
    public void delStudentByCourse(String courseId, String studentId, String teacherId) {
        utilService.checkCourseAndTeacher(courseId, teacherId);
        int i = studentDao.delStudentByCourse(courseId, studentId);
        if (i < 1) {
            throw new MyException(503, "delStudentByCourse", "更新失败");
        }
    }

    @Override
    public void updateStudentByGroup(String groupId, String studentId, Double score, String teacherId) {
        utilService.checkGroup(groupId, teacherId);
        int i = studentDao.updateStudentByGroup(groupId, studentId, score);
        if (i < 1) {
            throw new MyException(503, "updateStudentByGroup", "更新失败");
        }
    }

    @Override
    public void delStudentFromGroup(String groupId, String studentId, String teacherId) {
        utilService.checkGroup(groupId, teacherId);
        int i = studentDao.delStudentFromGroup(groupId, studentId);
        if (i < 1) {
            throw new MyException(502, "delStudentFromGroup", "删除失败");
        }
    }

    @Override
    public StudentBean queryStudentById(String studentId) {
        return studentDao.queryStudentById(studentId);
    }

    @Override
    public void addStudents(List<StudentBean> students) {
        StudentBean studentBean = null;
        for (int i = 0; i < students.size(); i++) {
            studentBean = students.get(i);
            if (utilService.checkStudent(studentBean.getId()) > 0) {
                throw new MyException(501, "第" + (i + 1) + "行 id: " + studentBean.getId() + " 已存在", "添加失败");
            }
        }
        int i = studentDao.addStudents(students);
        if (i < 1) {
            throw new MyException(501, "addStudents", "添加失败");
        }
    }

    @Override
    public void updateStudentInfo(String id, String name, String head, Integer gender) {
        int i = studentDao.updateStudentInfo(id, name, head, gender);
        if(i<1){
            int i1 = studentDao.initStudent(id, name, head, gender);
            if (i1 < 1) {
                throw new MyException(501, "initStudent", "添加失败");
            }
        }
    }

    @Override
    public void updateStudentGroupInCourseByCourse(String courseId, String studentId, String groupId) {
        int i = studentDao.updateStudentGroupInCourseByCourse(courseId, studentId, groupId);
        if (i < 1) {
            throw new MyException(503, "updateStudentGroupInCourseByCourse", "更新失败");
        }
    }


}
