package com.zut.interactivetools.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.*;
import com.zut.interactivetools.config.WXWorkConfig;
import com.zut.interactivetools.exception.ExceptionResponse;
import com.zut.interactivetools.exception.MyException;
import com.zut.interactivetools.service.CourseService;
import com.zut.interactivetools.service.DiscussService;
import com.zut.interactivetools.service.GroupService;
import com.zut.interactivetools.service.StudentService;
import com.zut.interactivetools.service.impl.QRService;
import com.zut.interactivetools.service.impl.UtilService;
import com.zut.interactivetools.service.impl.WXWorkService;
import com.zut.interactivetools.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class DiscussController {

    @Autowired
    DiscussService discussService;
    @Autowired
    UtilService utilService;
    @Autowired
    QRService qrService;
    @Autowired
    GroupService groupService;
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;
    @Autowired
    WXWorkService wxWorkService;
    
    /**
    * @Description: 获取讨论记录
    * @Param: [courseId, status, pageNum, pageSize, id, name, date, session]
    * @return: com.github.pagehelper.PageInfo<com.zut.interactivetools.bean.DiscussCourseBean>
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/teacher/discusses/{courseId}/{status}/{pageNum}")
    @ResponseBody
    public PageInfo<DiscussCourseBean> queryDiscussCourseByCourse(@PathVariable("courseId") String courseId, @PathVariable("status") Integer status,
                                                                  @PathVariable("pageNum") Integer pageNum, Integer pageSize, String id, String name,
                                                                  String date, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        PageInfo<DiscussCourseBean> discussCourseBeanPageInfo = discussService.queryDiscussCourseByCourse(pageNum, pageSize, courseId, teacher.getId(), status, id, name, date);
        return discussCourseBeanPageInfo;
    }

    /**
    * @Description: 添加讨论记录
    * @Param: [map, session]
    * @return: com.zut.interactivetools.exception.ExceptionResponse
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @Transactional
    @PostMapping("/teacher/discuss")
    @ResponseBody
    public ExceptionResponse addDiscussCourseByCourse(@RequestBody Map map, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        String courseId = (String) map.get("courseId");
        String name = (String) map.get("name");
        String discussBody = (String) map.get("discussBody");
        List<String> groupId = (List<String>) map.get("groupId");
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
        if(groupStudentBeans==null||groupStudentBeans.size()<1){
            throw new MyException(508,"","参与人数为0");
        }
        discussService.addDiscussCourseByCourse(id, courseId, teacher.getId(), name, discussBody, new Date(), g.substring(1));
        discussService.addDiscussStudentByCourse(id, groupStudentBeans, teacher.getId(), courseId);
        CourseBean courseBean = courseService.queryCourseInfoById(courseId, teacher.getId());
        Date date = new Date();
        //企业微信消息推送
        TextCardBean textCardBean = new TextCardBean();
        TextCardInfoBean textCardInfoBean = new TextCardInfoBean();
        textCardBean.setAgentid(Integer.parseInt(WXWorkConfig.AGENT_ID_STU));
        textCardInfoBean.setTitle("讨论通知");
        textCardInfoBean.setDescription("<div class=\"gray\">" + date.toString() + "</div> <div class=\"normal\">课程名：" + courseBean.getName() + "课程号：" +
                courseBean.getNumber() + "教师：" + courseBean.getTeacher().getName() + "</div><div class=\"highlight\">参加讨论</div>");
        textCardBean.setTouser(sids.toString());
        textCardInfoBean.setUrl(WXWorkConfig.URL+"/wxwork/student/stuTalk?discussId=" + id);
        textCardInfoBean.setBtntxt("点击讨论");
        textCardBean.setTextCard(textCardInfoBean);
        wxWorkService.sendStuTextCard(textCardBean);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setMsg(id);
        exceptionResponse.setDesc("发布成功");
        return exceptionResponse;
    }

    /**
    * @Description: 获取讨论二维码
    * @Param: [discussId, courseId, session]
    * @return: org.springframework.http.ResponseEntity<byte[]>
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/teacher/discuss/code/{courseId}/{discussId}")
    @ResponseBody
    public ResponseEntity<byte[]> getDiscussCode(@PathVariable String discussId,@PathVariable String courseId, HttpSession session){
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        utilService.checkDiscussCourse(discussId,courseId,teacher.getId());
        String info = WXWorkConfig.URL+"/wxwork/student/stuTalk?discussId="+discussId;
        ResponseEntity<byte[]> qrImage = qrService.getQRImage(info);
        return qrImage;
    }

    /**
    * @Description: 结束讨论
    * @Param: [id, courseId, session]
    * @return: com.zut.interactivetools.exception.ExceptionResponse
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @DeleteMapping("/teacher/discuss/stop")
    @ResponseBody
    public ExceptionResponse stopDiscussByCourse(String id, String courseId, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        discussService.outDiscussCourseByCourse(id, courseId, teacher.getId());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("结束成功");
        return exceptionResponse;
    }

    /**
    * @Description: 获取讨论学生信息
    * @Param: [courseId, discussId, status, session]
    * @return: java.util.List<com.zut.interactivetools.bean.DiscussStudentBean>
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/teacher/discuss/studentInfo/{courseId}/{discussId}/{status}")
    @ResponseBody
    public List<DiscussStudentBean> getDiscussStudentByDiscuss(@PathVariable("courseId") String courseId, @PathVariable("discussId") String discussId,
                                                               @PathVariable("status") Integer status, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        List<DiscussStudentBean> discussStudentBeans = discussService.queryDiscussStudentInfoByDiscuss(discussId, status, teacher.getId(), courseId);
        for (DiscussStudentBean discussStudentBean : discussStudentBeans) {
            Object parse = JSON.parse(discussStudentBean.getBody());
            List<DiscussBodyBean> list = new ArrayList<>();
            if(parse!=null){
                list = JSON.parseArray(parse.toString(), DiscussBodyBean.class);
            }
            discussStudentBean.setDiscussBody(list);
        }
        return discussStudentBeans;
    }

    /**
    * @Description: 教师获取讨论主题
    * @Param: [discussId, session]
    * @return: com.zut.interactivetools.bean.DiscussCourseBean
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/teacher/discuss/theme/{discussId}")
    @ResponseBody
    public DiscussCourseBean getDiscussThemeByTeacher(@PathVariable String discussId, HttpSession session){
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        DiscussCourseBean discussCourseBean = discussService.queryDiscussCourseThemeByIdTeacher(discussId, teacher.getId());
        return discussCourseBean;
    }

    /**
    * @Description: 学生获取讨论主题
    * @Param: [discussId, session]
    * @return: java.util.Map
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/wxwork/student/discuss/theme/{discussId}")
    @ResponseBody
    public Map getDiscussThemeByStudent(@PathVariable String discussId, HttpSession session){
        UserInforBean userInfo = (UserInforBean) session.getAttribute("UserInfo");
        utilService.checkDiscussStudent(discussId,userInfo.getUserid());
        DiscussCourseBean discussCourseBean = discussService.queryDiscussCourseThemeByIdStudent(discussId);
        Map<String,Object> map = new HashMap();
        map.put("discuss",discussCourseBean);
        map.put("name",userInfo.getName());
        map.put("head",userInfo.getThumb_avatar());
        return map;
    }

    /**
    * @Description: 学生获取讨论信息（自己的讨论）
    * @Param: [discussId, status, session]
    * @return: java.util.List<com.zut.interactivetools.bean.DiscussBodyBean>
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/wxwork/student/discussBody/{discussId}/{status}")
    @ResponseBody
    public List<DiscussBodyBean> getDiscussBodyByStudent(@PathVariable("discussId") String discussId, @PathVariable("status") Integer status, HttpSession session) {
        UserInforBean userInfor = (UserInforBean) session.getAttribute("UserInfo");
        List<String> str = discussService.queryDiscussStudentBodyByStudent(discussId, status, userInfor.getUserid());
        String s = null;
        if(str!=null&&str.size()>0){
            s=str.get(0);
        }
        JSONObject jsonObject = null;
        List<DiscussBodyBean> list = null;

        if (s != null && !"".equals(s)) {
            Object parse = JSON.parse(s);
            list = JSON.parseArray(s, DiscussBodyBean.class);
        }else {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
    * @Description: 学生发送讨论信息
    * @Param: [discussId, discussBody, session]
    * @return: com.zut.interactivetools.exception.ExceptionResponse
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @PutMapping("/wxwork/student/discussBody")
    @ResponseBody
    public ExceptionResponse sendDiscussBodyByStudent(String discussId, String discussBody, HttpSession session) {
        UserInforBean userInfor = (UserInforBean) session.getAttribute("UserInfo");
        utilService.checkDiscussCourseAlive(discussId);
        List<String> str = discussService.queryDiscussStudentBodyByStudent(discussId, 0, userInfor.getUserid());
        String s = null;
        if(str!=null&&str.size()>=1){
            s = str.get(0);
        }
        JSONObject jsonObject = null;
        List<DiscussBodyBean> list = null;

        if (s != null && !"".equals(s)) {
            Object parse = JSON.parse(s);
            list = JSON.parseArray(s, DiscussBodyBean.class);
        }else {
            list = new ArrayList<>();
        }


        DiscussBodyBean discussBodyBean = new DiscussBodyBean();
        discussBodyBean.setDate(new Date());
        discussBodyBean.setBody(discussBody);
        list.add(discussBodyBean);
        String s1 = JSON.toJSONString(list);
        discussService.updateDiscussStudentByStudent(discussId,userInfor.getUserid(),s1);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("发送成功");
        return exceptionResponse;
    }

    /**
    * @Description: 获取讨论记录
    * @Param: [discussId, status, courseId, session]
    * @return: java.util.List<com.zut.interactivetools.bean.DiscussStudentBean>
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/teacher/discuss/students/{courseId}/{discussId}/{status}")
    @ResponseBody
    public List<DiscussStudentBean> getDiscussStudentByCourse(@PathVariable String discussId, @PathVariable Integer status,
                                                              @PathVariable String courseId, HttpSession session){
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        List<DiscussStudentBean> discussStudentBeans = discussService.queryDiscussStudentByCourse(discussId, status, teacher.getId(), courseId);
        return discussStudentBeans;
    }

    /**
    * @Description: 教师确认学生讨论成绩
    * @Param: [map, session]
    * @return: com.zut.interactivetools.exception.ExceptionResponse
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @Transactional
    @PutMapping("/teacher/discuss/student/confirm")
    @ResponseBody
    public ExceptionResponse confirmDiscussStudentByCourse(@RequestBody Map map, HttpSession session){
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        String courseId = (String) map.get("courseId");
        String discussId = (String) map.get("discussId");
        Object o = map.get("students");
        String s = JSON.toJSONString(o);
        List<StudentBean> studentBeans = JSON.parseArray(s, StudentBean.class);
        utilService.checkDiscussCourse(discussId,courseId,teacher.getId());
        discussService.confirmDiscuss(discussId,courseId,teacher.getId());
        for (StudentBean studentBean : studentBeans) {
            discussService.confirmDiscussStudentByCourse(studentBean.getScore(),discussId,studentBean.getId(),studentBean.getGroupId());
            groupService.updateScore(studentBean.getGroupId(),studentBean.getId(),studentBean.getScore(),teacher.getId());
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("确认成功");
        return exceptionResponse;
    }


}
