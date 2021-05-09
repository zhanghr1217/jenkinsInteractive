package com.zut.interactivetools.service;

import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface SignService {
    PageInfo<SignBean> querySignByCourse(int pageNum, int pageSize, String courseId, String teacherId, Integer status, String id,
                                         String name, Integer type, String begin, Double score, Integer submitStatus);

    void delSignByCourse(String id, String courseId, String teacherId);

    void updateSignByCourse(String id, String courseId, String teacherId, String name);

    void addSignByCourse(String id, String courseId, String teacherId, String name, Integer limit, Integer type, Double score, String groupId,Integer submitStatus,Date date);

    double querySignScoreById(String id);

    void addQRCode(String id, String token, Date date, String signId);

    void killSign(String id, String courseId, String teacherId);

    void killQRCode(String signId, String courseId, String teacherId);

    List<SignRecordBean> querySignRecordByCourse(String signId, Integer status, String studentId, String studentName, String className,
                                                 String majorName, Integer confirm, String teacherId, String courseId);

    void delSignRecordByCourse(String signId, String studentId, String groupId, String teacherId, String courseId);

    void updateSignRecordByCourse(String signId, String studentId, String groupId, Integer record, Double score, String teacherId, String courseId);

    void confirmSignRecordByCourse(String signId, String studentId, String groupId, Integer record, Double score);

    void addSignRecordByCourse(String signId, Integer type, List<GroupStudentBean> list, String teacherId, String courseId);

    void updateSignRecordByStudent(String signId, String studentId, Date sign_date, String extra, Integer record, Double score);

    ResponseEntity<byte[]> QRSign(String signId, String token, Integer type, String courseId, String teacherId);

    List<StudentBean> querySignStudentInfoById(String signId, Integer record, String teacherId, String courseId);

    List<StudentBean> querySignStudentDetailInfoById(String signId, Integer record, String teacherId, String courseId);

    void addSignLocation(String signId, String location, Double longitude, Double latitude, String info, String teacherId, String courseId);

    SignLocationBean getSignLocation(String signId);

    void confirmSign(String id, String courseId, String teacherId);

    PageInfo<SignBean> querySignCacheByCourse(int pageNum, int pageSize, String courseId, String teacherId, Integer status, String id,
                                              String name, Integer type, Double score);

    void updateSubmitStatus(String id, String courseId, String teacherId);

    List<SignBean> querySignByCensus(String courseId, String teacherId, Integer status, Integer censusType);

    List<StudentBean> querySignStudentByCensus(List<SignBean> list);

    List<SignBean> querySignCountByCensus(List<SignBean> list);

}
