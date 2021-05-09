package com.zut.interactivetools.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.*;
import com.zut.interactivetools.config.WXWorkConfig;
import com.zut.interactivetools.dao.SignCacheDao;
import com.zut.interactivetools.dao.SignDao;
import com.zut.interactivetools.exception.MyException;
import com.zut.interactivetools.service.GroupService;
import com.zut.interactivetools.service.SignService;
import com.zut.interactivetools.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class SignServiceImpl implements SignService {

    @Autowired
    SignDao signDao;
    @Autowired
    UtilService utilService;
    @Autowired
    GroupService groupService;
    @Autowired
    QRService qrService;
    @Autowired
    SignCacheDao signCacheDao;

    @Override
    public PageInfo<SignBean> querySignByCourse(int pageNum, int pageSize, String courseId, String teacherId, Integer status,
                                                String id, String name, Integer type, String begin, Double score, Integer submitStatus) {
        PageHelper.startPage(pageNum, pageSize);
        List<SignBean> signBeans = signDao.querySignByCourse(courseId, teacherId, status, id, name, type, begin, score, submitStatus);
        List<GroupBean> groupBeans = groupService.queryGroupMapByCourseNoStatus(courseId, teacherId);
        HashMap<String, String> map = new HashMap<>();
        for (GroupBean groupBean : groupBeans) {
            map.put(groupBean.getId(),groupBean.getName());
        }
        for (SignBean signBean : signBeans) {
            if(signBean.getGroupId()!=null&&!"".equals(signBean.getGroupId())){
                String[] split = signBean.getGroupId().split("\\|");
                List<String> gname = new ArrayList<>();
                String g = null;
                for (String s : split) {
                  if((g=map.get(s))!=null){
                      gname.add(g);
                  }
                }
                signBean.setGroupName(gname);
            }else {
                signBean.setGroupName(new ArrayList<>());
            }
        }
        return new PageInfo<>(signBeans);
    }

    @Override
    public void delSignByCourse(String id, String courseId, String teacherId) {
        int i = signDao.delSignByCourse(id, courseId, teacherId);
        if (i < 1) {
            throw new MyException(502, "delSignByCourse", "删除失败");
        }
    }

    @Override
    public void updateSignByCourse(String id, String courseId, String teacherId, String name) {
        int i = signDao.updateSignByCourse(id, courseId, teacherId, name);
        if (i < 1) {
            throw new MyException(503, "updateSignByCourse", "更新失败");
        }
    }

    @Override
    public void addSignByCourse(String id, String courseId, String teacherId, String name, Integer limit, Integer type,
                                Double score, String groupId, Integer submitStatus,Date date) {
        utilService.checkCourseAndTeacher(courseId, teacherId);
        int i = signDao.addSignByCourse(id, courseId, teacherId, name, date, limit, type, score, groupId,submitStatus);
        if (i < 1) {
            throw new MyException(501, "addSignByCourse", "添加失败");
        }
    }

    @Override
    public double querySignScoreById(String id) {
        return signDao.querySignScoreById(id);
    }

    @Override
    public void addQRCode(String id, String token, Date date, String signId) {
        int i = signDao.addQRCode(id, token, date, signId);
        if (i < 1) {
            throw new MyException(501, "addQRCode", "添加失败");
        }
    }

    @Override
    public void killSign(String id, String courseId, String teacherId) {
        int i = signDao.killSign(id, courseId, teacherId);
        if (i < 1) {
            throw new MyException(503, "killSign", "更新失败");
        }
    }

    @Override
    public void killQRCode(String signId, String courseId, String teacherId) {
        int i = signDao.killQRCode(signId);

    }

    @Override
    public List<SignRecordBean> querySignRecordByCourse(String signId, Integer status, String studentId, String studentName,
                                                        String className, String majorName, Integer confirm, String teacherId, String courseId) {
        utilService.checkSignTeacher(signId, courseId, teacherId);
        return signDao.querySignRecordByCourse(signId, status, studentId, studentName, className, majorName, confirm);
    }

    @Override
    public void delSignRecordByCourse(String signId, String studentId, String groupId, String teacherId, String courseId) {
        utilService.checkSignTeacher(signId, courseId, teacherId);
        int i = signDao.delSignRecordByCourse(signId, studentId, groupId);
        if (i < 1) {
            throw new MyException(502, "delSignByCourse", "删除失败");
        }
    }

    @Override
    public void updateSignRecordByCourse(String signId, String studentId, String groupId, Integer record, Double score, String teacherId, String courseId) {
        utilService.checkSignTeacher(signId,courseId, teacherId);
        int i = signDao.updateSignRecordByCourse(signId, studentId, groupId, record, score);
        if (i < 1) {
            throw new MyException(503, "updateSignRecordByCourse", "更新失败");
        }
    }

    @Override
    public void confirmSignRecordByCourse(String signId, String studentId, String groupId, Integer record, Double score) {
        int i = signDao.confirmSignRecordByCourse(signId, studentId, groupId, record, score);
        if (i < 1) {
            throw new MyException(503, "confirmSignRecordByCourse", "更新失败");
        }
    }

    @Override
    public void addSignRecordByCourse(String signId, Integer type, List<GroupStudentBean> list, String teacherId, String courseId) {
//        utilService.checkSignTeacher(signId,courseId,teacherId);
        for (GroupStudentBean groupStudentBean : list) {
            utilService.checkGroupStudent(groupStudentBean.getGid(),groupStudentBean.getSid());
        }
        int i = signDao.addSignRecordByCourse(signId, type, list);
        if (i < 1) {
            throw new MyException(501, "addSignRecordByCourse", "添加失败");
        }
    }

    @Override
    public void updateSignRecordByStudent(String signId, String studentId, Date sign_date, String extra, Integer record, Double score) {
        utilService.checkSignAliveById(signId);
        int i = signDao.updateSignRecordByStudent(signId, studentId, sign_date, extra, record, score);
        if (i < 1) {
            throw new MyException(503, "updateSignRecordByStudent", "更新失败");
        }
    }

    @Override
    public ResponseEntity<byte[]> QRSign(String signId, String token, Integer type, String teacherId, String courseId) {
        String info = null;
        if(type==1){
            info = WXWorkConfig.URL+ "/wxwork/student/toSign?signId="+signId+"&type="+type+"&token="+token;
        }else if(type==2){
            info = WXWorkConfig.URL+"/wxwork/student/location?signId="+signId+"&type="+type;
        }
        ResponseEntity<byte[]> qrImage = qrService.getQRImage(info);
        return qrImage;
    }

    @Override
    public List<StudentBean> querySignStudentInfoById(String signId, Integer record, String teacherId, String courseId) {
        utilService.checkSignTeacher(signId,courseId,teacherId);
        return signDao.querySignStudentInfoById(signId, record);
    }

    @Override
    public List<StudentBean> querySignStudentDetailInfoById(String signId, Integer record, String teacherId, String courseId) {
        utilService.checkSignTeacher(signId,courseId,teacherId);
        return signDao.querySignStudentDetailInfoById(signId, record);
    }

    @Override
    public void addSignLocation(String signId, String location, Double longitude, Double latitude, String info, String teacherId, String courseId) {
        utilService.checkSignTeacherAndAlive(signId, courseId, teacherId, 2);
        int i1 = signDao.checkSignLocation(signId);
        if (i1 < 1) {
            int i = signDao.addSignLocation(signId, location, longitude, latitude, info);
            if (i < 1) {
                throw new MyException(501, "addSignLocation", "添加失败");
            }
        }else {
            int i = signDao.updateSignLocation(signId, location, longitude, latitude, info);
            if (i < 1) {
                throw new MyException(503, "updateSignLocation", "修改失败");
            }
        }
    }

    @Override
    public SignLocationBean getSignLocation(String signId) {
        return signDao.getSignLocation(signId);
    }

    @Override
    public void confirmSign(String id, String courseId, String teacherId) {
        int i = signDao.confirmSign(id, courseId, teacherId);
        if (i < 1) {
            throw new MyException(503, "confirmSign", "修改失败");
        }
    }

    @Override
    public PageInfo<SignBean> querySignCacheByCourse(int pageNum, int pageSize, String courseId, String teacherId, Integer status, String id, String name, Integer type, Double score) {
        PageHelper.startPage(pageNum, pageSize);
        List<SignBean> signBeans = signDao.querySignCacheByCourse(courseId, teacherId, status, id, name, type, score);
        //将课程下所有班级存入hashmap
        List<GroupBean> groupBeans = groupService.queryGroupMapByCourseNoStatus(courseId, teacherId);
        HashMap<String, String> map = new HashMap<>();
        for (GroupBean groupBean : groupBeans) {
            map.put(groupBean.getId(),groupBean.getName());
        }
        //通过hashmap查找所对应的班级信息
        //数据库中group_id 格式 g1|g2|g3...
        for (SignBean signBean : signBeans) {
            if(signBean.getGroupId()!=null&&!"".equals(signBean.getGroupId())){
                String[] split = signBean.getGroupId().split("\\|");
                List<String> gname = new ArrayList<>();
                String g = null;
                for (String s : split) {
                    if((g=map.get(s))!=null){
                        gname.add(g);
                    }
                }
                signBean.setGroupName(gname);
            }else {
                signBean.setGroupName(new ArrayList<>());
            }
        }
        return new PageInfo<>(signBeans);
    }

    @Override
    public void updateSubmitStatus(String id, String courseId, String teacherId) {
        int i = signDao.updateSubmitStatus(id, courseId, teacherId);
        if (i < 1) {
            throw new MyException(503, "updateSubmitStatus", "修改失败");
        }
    }


    @Override
    public List<StudentBean> querySignStudentByCensus(List<SignBean> list) {
        return signDao.querySignStudentByCensus(list);
    }

    @Override
    public List<SignBean> querySignCountByCensus(List<SignBean> list) {
        return signDao.querySignCountByCensus(list);
    }

    @Override
    public List<SignBean> querySignByCensus(String courseId, String teacherId, Integer status, Integer censusType) {
        return signDao.querySignByCensus(courseId,teacherId, status, censusType);
    }
}
