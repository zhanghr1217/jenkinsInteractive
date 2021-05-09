package com.zut.interactivetools.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.*;
import com.zut.interactivetools.config.WXWorkConfig;
import com.zut.interactivetools.dao.SignCacheDao;
import com.zut.interactivetools.exception.ExceptionResponse;
import com.zut.interactivetools.exception.MyException;
import com.zut.interactivetools.service.*;
import com.zut.interactivetools.service.impl.*;
import com.zut.interactivetools.util.SubmitTaskCreateUtil;
import com.zut.interactivetools.util.TextCardCreateUtil;
import com.zut.interactivetools.util.UUIDUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class SignController {

    @Autowired
    SignService signService;
    @Autowired
    SignCacheService signCacheService;
    @Autowired
    UtilService utilService;
    @Autowired
    WXWorkService wxWorkService;
    @Autowired
    CourseService courseService;
    @Autowired
    StudentService studentService;
    @Autowired
    GroupService groupService;
    @Autowired
    RedisService redisService;
    @Autowired
    LocationService locationService;

    /**
     * @Description: 获取签到记录
     * @Param: [courseId, status, pageNum, pageSize, id, name, type, begin, score, session]
     * @return: com.github.pagehelper.PageInfo<com.zut.interactivetools.bean.SignBean>
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @GetMapping("/teacher/signs/{courseId}/{status}/{submitStatus}/{pageNum}")
    @ResponseBody
    public PageInfo<SignBean> getSignByCourse(@PathVariable String courseId, @PathVariable Integer status,
                                              @PathVariable int pageNum, int pageSize, String id, String name, Integer type,
                                              String begin, Double score, @PathVariable Integer submitStatus, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        PageInfo<SignBean> signBeanPageInfo = signService.querySignByCourse(pageNum, pageSize, courseId, teacher.getId(), status, id, name, type, begin, score,submitStatus);
        return signBeanPageInfo;
    }

    /**
     * @Description: 创建签到，位置签到教师需要先定位
     * @Param: [map, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @Transactional
    @PostMapping("/teacher/sign")
    @ResponseBody
    public ExceptionResponse createSignByCourse(@RequestBody Map map, HttpSession session) {
        Integer limit = null;
        String courseId = (String) map.get("courseId");
        String name = (String) map.get("name");
        List<String> groupId = (List<String>) map.get("groupId");
        Integer type = Integer.parseInt(map.get("type").toString());
        Integer submitType = Integer.parseInt(map.get("submitType").toString());
        Date submitTime = null;
        String location = null;
        Double longitude = null;
        Double latitude = null;

        if (submitType == 1) {
            try {
                submitTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(map.get("submitTime").toString());

                if (new Date().getTime() > submitTime.getTime()) {
                    throw new MyException(505, "date error", "请选择正确的预定时间");
                }
            } catch (ParseException e) {
                throw new MyException(505, "date parse", "时间格式化异常");
            }
        }
        if (type != 1 && type != 2) {
            throw new MyException(506, "type:" + type, "参数错误");
        }
        if (map.get("limit") != null && !"".equals(map.get("limit"))) {
            limit = Integer.parseInt(map.get("limit").toString());
        }
        if (type == 2) {
            limit = 0;
            location = map.get("location").toString();
            longitude = Double.parseDouble(map.get("longitude").toString());
            latitude = Double.parseDouble(map.get("latitude").toString());
        }
        Double score = Double.parseDouble(map.get("score").toString());
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        String g = "";
        for (String s : groupId) {
            g += "|";
            g += s;
        }
        String id = UUIDUtil.getUUID();
        List<GroupStudentBean> groupStudentBeans = new ArrayList<>();
        StringBuilder sids = new StringBuilder();
        for (String s : groupId) {
            List<String> list = studentService.queryStudentsIdByGroup(s, teacher.getId(), 0);
            for (String s1 : list) {
                sids.append(s1).append("|");
                GroupStudentBean groupStudentBean = new GroupStudentBean();
                groupStudentBean.setGid(s);
                groupStudentBean.setSid(s1);
                groupStudentBeans.add(groupStudentBean);
            }
        }
        if (groupStudentBeans == null || groupStudentBeans.size() < 1) {
            throw new MyException(508, "", "参与人数为0");
        }
        CourseBean courseBean = courseService.queryCourseInfoById(courseId, teacher.getId());
        //发送推送，1为动态码签到，2为位置签到
        TextCardBean textCardBean = null;
        if (type == 1 && submitType == 0) {
            signService.addSignByCourse(id, courseId, teacher.getId(), name, limit, type, score, g.substring(1),submitType,new Date());
            signService.addSignRecordByCourse(id, type, groupStudentBeans, teacher.getId(), courseId);
            textCardBean = TextCardCreateUtil.createSimpleCodeSignTextCard(sids.toString(), courseBean.getName(), courseBean.getNumber(), teacher.getName());
            wxWorkService.sendStuTextCard(textCardBean);
        } else if (type == 2 && submitType == 0) {
            signService.addSignByCourse(id, courseId, teacher.getId(), name, limit, type, score, g.substring(1),submitType,new Date());
            signService.addSignLocation(id, location, longitude, latitude, location, teacher.getId(), courseId);
            signService.addSignRecordByCourse(id, type, groupStudentBeans, teacher.getId(), courseId);
            textCardBean = TextCardCreateUtil.createSimpleLocationSignTextCard(sids.toString(), courseBean.getName(), courseBean.getNumber(), teacher.getName(), id);
            wxWorkService.sendStuTextCard(textCardBean);
        } else if (type == 1 && submitType == 1) {
            signService.addSignByCourse(id, courseId, teacher.getId(), name, limit, type, score, g.substring(1),submitType,submitTime);
            signService.addSignRecordByCourse(id, type, groupStudentBeans, teacher.getId(), courseId);
//            textCardBean = TextCardCreateUtil.createSimpleCodeSignTextCard(sids.toString(), courseBean.getName(), courseBean.getNumber(), teacher.getName());
            SubmitTaskBean simpleSignSubmitTaskBean = SubmitTaskCreateUtil.createSimpleSignSubmitTaskBean(id, submitTime.getTime(), teacher.getId(),courseId, courseBean.getNumber(), sids.toString(), TaskEnum.CODE_SIGN, courseBean.getName(), teacher.getName());
            signCacheService.addSignCache(id,submitTime.getTime(),TaskEnum.CODE_SIGN,sids.toString(),teacher.getId(),courseId,courseBean.getNumber(),teacher.getName(),courseBean.getName());
            redisService.putTask(simpleSignSubmitTaskBean);
        } else if (type == 2 && submitType == 1) {
            signService.addSignByCourse(id, courseId, teacher.getId(), name, limit, type, score, g.substring(1),submitType,submitTime);
            signService.addSignLocation(id, location, longitude, latitude, location, teacher.getId(), courseId);
            signService.addSignRecordByCourse(id, type, groupStudentBeans, teacher.getId(), courseId);
//            textCardBean = TextCardCreateUtil.createSimpleLocationSignTextCard(sids.toString(), courseBean.getName(), courseBean.getNumber(), teacher.getName(), id);
            SubmitTaskBean simpleSignSubmitTaskBean = SubmitTaskCreateUtil.createSimpleSignSubmitTaskBean(id, submitTime.getTime(), teacher.getId(),courseId, courseBean.getNumber(), sids.toString(), TaskEnum.LOCATION_SIGN, courseBean.getName(), teacher.getName());
            signCacheService.addSignCache(id,submitTime.getTime(),TaskEnum.LOCATION_SIGN,sids.toString(),teacher.getId(),courseId,courseBean.getNumber(),teacher.getName(),courseBean.getName());
            redisService.putTask(simpleSignSubmitTaskBean);

        }

        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setMsg(id);
        exceptionResponse.setDesc("发布成功");
        return exceptionResponse;
    }

    /**
     * @Description: 获取签到二维码
     * @Param: [signId, courseId, type, abc, session]
     * @return: org.springframework.http.ResponseEntity<byte   [   ]>
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @Transactional
    @GetMapping("/teacher/sign/code/{signId}/{courseId}/{type}")
    @ResponseBody
    public ResponseEntity<byte[]> getSignQR(@PathVariable("signId") String signId, @PathVariable("courseId") String courseId, @PathVariable("type") Integer type,
                                            String abc, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        utilService.checkSignTeacherAndAlive(signId, courseId, teacher.getId(), type);
        String uuid = UUIDUtil.getUUID();
        signService.killQRCode(signId, courseId, teacher.getId());
        String info = WXWorkConfig.URL + "/student/toSign?signId=" + signId + "&type=" + type + "&token=" + uuid;
        signService.addQRCode(uuid, info, new Date(), signId);
        ResponseEntity<byte[]> responseEntity = signService.QRSign(signId, uuid, type, teacher.getId(), courseId);
        return responseEntity;
    }

    /**
     * @Description: 获取签到学生信息
     * @Param: [signId, courseId, record, session]
     * @return: java.util.List<com.zut.interactivetools.bean.StudentBean>
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @GetMapping("/teacher/sign/stuInfo/{signId}/{courseId}/{record}")
    @ResponseBody
    public List<StudentBean> getSignStuInfo(@PathVariable("signId") String signId, @PathVariable("courseId") String courseId, @PathVariable("record") Integer record, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        List<StudentBean> studentBeans = signService.querySignStudentInfoById(signId, record, teacher.getId(), courseId);
        return studentBeans;
    }

    /**
     * @Description: 修改学生签到记录
     * @Param: [signId, studentId, groupId, record, score, courseId, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @PutMapping("/teacher/sign/stuInfo")
    @ResponseBody
    public ExceptionResponse updateSignRecordByCourse(String signId, String studentId, String groupId, Integer record,
                                                      Double score, String courseId, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        signService.updateSignRecordByCourse(signId, studentId, groupId, record, score, teacher.getId(), courseId);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("修改成功");
        return exceptionResponse;
    }

    /**
     * @Description: 获取学生签到或未签到信息
     * @Param: [signId, courseId, record, session]
     * @return: java.util.List<com.zut.interactivetools.bean.StudentBean>
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @GetMapping("/teacher/sign/stuDetailInfo/{signId}/{courseId}/{record}")
    @ResponseBody
    public List<StudentBean> getSignStuDetailInfo(@PathVariable("signId") String signId, @PathVariable("courseId") String courseId, @PathVariable("record") Integer record, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        List<StudentBean> studentBeans = signService.querySignStudentDetailInfoById(signId, record, teacher.getId(), courseId);
        return studentBeans;
    }

    /**
     * @Description: 教师确认学生签到成绩
     * @Param: [map, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @Transactional
    @PutMapping("/teacher/sign/record/confirm")
    @ResponseBody
    public ExceptionResponse confirmSignRecordByCourse(@RequestBody Map map, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        String courseId = (String) map.get("courseId");
        String signId = (String) map.get("signId");
        Object o = map.get("students");
        String s = JSON.toJSONString(o);
        List<StudentBean> studentBeans = JSON.parseArray(s, StudentBean.class);
        signService.confirmSign(signId, courseId, teacher.getId());
        for (StudentBean studentBean : studentBeans) {
            signService.confirmSignRecordByCourse(signId, studentBean.getId(), studentBean.getGroupId(), studentBean.getRecord(), studentBean.getScore());
            groupService.updateScore(studentBean.getGroupId(), studentBean.getId(), studentBean.getScore(), teacher.getId());
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("确认成功");
        return exceptionResponse;
    }

    /**
     * @Description: 结束签到
     * @Param: [signId, courseId, type, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @Transactional
    @PutMapping("/teacher/sign/stop")
    @ResponseBody
    public ExceptionResponse stopSign(String signId, String courseId, Integer type, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        if (type == 1) {
            signService.killQRCode(signId, courseId, teacher.getId());
            signService.killSign(signId, courseId, teacher.getId());
        } else if (type == 2) {
            signService.killSign(signId, courseId, teacher.getId());
        } else {
            throw new MyException(509, "", "未知参数");
        }

        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("关闭成功");
        return exceptionResponse;
    }

    /**
     * @Description: 学生签到
     * @Param: [signId, type, token, location, info, longitude, latitude, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @GetMapping("/wxwork/student/toSign")
    public String studentSign(String signId, Integer type, String token, String location, String info, Double longitude,
                                         Double latitude, HttpSession session, Model model) {
        UserInforBean stu = (UserInforBean) session.getAttribute("UserInfo");
        try {
            if (type == 1) {
                utilService.checkAliveQRCodeById(token, signId);
                utilService.checkSignAliveById(signId);
                double score = signService.querySignScoreById(signId);
                signService.updateSignRecordByStudent(signId, stu.getUserid(), new Date(), token, 0, score);
            } else if (type == 2) {
                utilService.checkSignAliveById(signId);
                SignLocationBean signLocation = signService.getSignLocation(signId);
                if (signLocation == null) {
                    throw new MyException(507, "", "教师未设置位置");
                }
                //与教师位置小于50m即可签到成功
                double distance = locationService.GetDistance(latitude, longitude, signLocation.getLatitude(), signLocation.getLongitude());
                if (distance <= 50.0) {
                    double score = signService.querySignScoreById(signId);
                    String extra = longitude + "|" + latitude + "|" + location + "|" + info;
                    signService.updateSignRecordByStudent(signId, stu.getUserid(), new Date(), extra, 0, score);
                } else {
                    throw new MyException(508, "", "离签到地点"+distance+"m");
                }

            } else {
                throw new MyException(506, "type:" + type, "参数错误");
            }

        }catch (MyException e){
            model.addAttribute("msg",e);
            model.addAttribute("stu",stu);
            model.addAttribute("now",new Date());
            return "WXWork/phoneLogin";
        }

        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("签到成功");
        model.addAttribute("msg",exceptionResponse);
        model.addAttribute("stu",stu);
        model.addAttribute("now",new Date());
        return "WXWork/phoneLogin";

    }

    /**
     * @Description: 教师设置位置签到位置
     * @Param: [signId, courseId, location, info, longitude, latitude, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @PutMapping("/wxwork/teacher/sign/setLocation")
    @ResponseBody
    public ExceptionResponse setSignLocation(String signId, String courseId, String location, String info, Double longitude, Double latitude, HttpSession session) {
        UserInforBean user = (UserInforBean) session.getAttribute("UserInfo");
        signService.addSignLocation(signId, location, longitude, latitude, info, user.getUserid(), courseId);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("设置成功");
        return exceptionResponse;
    }
    
    @GetMapping("/teacher/sign/locations/{status}")
    @ResponseBody
    public List<LocationBean> getLocations(@PathVariable Integer status){
        List<LocationBean> allLocations = locationService.getAllLocations(status);
        return allLocations;
    }

    @Transactional
    @DeleteMapping("/teacher/sign/cache/{courseId}/{signId}")
    @ResponseBody
    public ExceptionResponse cancelReserveSign(@PathVariable String courseId,@PathVariable String signId,HttpSession session){
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        SubmitTaskBean signCacheById = signCacheService.getSignCacheById(signId, teacher.getId(), courseId);
        signCacheService.delCache(signId,teacher.getId(),courseId);
        signService.updateSubmitStatus(signId,courseId,teacher.getId());
        redisService.removeTask(signCacheById);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("取消成功");
        return exceptionResponse;
    }

    @GetMapping("/teacher/sign/census/{courseId}/{censusType}")
    @ResponseBody
    public CensusBean getSignIdByCensus(@PathVariable String courseId, @PathVariable Integer censusType,HttpSession session){
        TeacherBean teacherBean = (TeacherBean) session.getAttribute("teacher");
        CensusBean censusBean = new CensusBean();
        List<SignBean> list = signService.querySignByCensus(courseId, teacherBean.getId(), 0, censusType);
        List<StudentBean> studentBeans = signService.querySignStudentByCensus(list);
        List<SignBean> signBeans = signService.querySignCountByCensus(list);
        censusBean.setSigns(signBeans);
        censusBean.setStudents(studentBeans);
        return censusBean;
    }

}
