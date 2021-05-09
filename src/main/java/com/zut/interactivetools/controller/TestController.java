package com.zut.interactivetools.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.*;
import com.zut.interactivetools.config.WXWorkConfig;
import com.zut.interactivetools.exception.ExceptionResponse;
import com.zut.interactivetools.exception.MyException;
import com.zut.interactivetools.service.*;
import com.zut.interactivetools.service.impl.QRService;
import com.zut.interactivetools.service.impl.RedisService;
import com.zut.interactivetools.service.impl.UtilService;
import com.zut.interactivetools.service.impl.WXWorkService;
import com.zut.interactivetools.util.SubmitTaskCreateUtil;
import com.zut.interactivetools.util.TextCardCreateUtil;
import com.zut.interactivetools.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {
    @Autowired
    TestService testService;
    @Autowired
    LibraryService libraryService;
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;
    @Autowired
    WXWorkService wxWorkService;
    @Autowired
    UtilService utilService;
    @Autowired
    QRService qrService;
    @Autowired
    GroupService groupService;
    @Autowired
    SignCacheService signCacheService;
    @Autowired
    RedisService redisService;

    /**
    * @Description: 创建试卷
    * @Param: [map, session]
    * @return: com.zut.interactivetools.exception.ExceptionResponse
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @PostMapping("/teacher/course/test")
    @ResponseBody
    public ExceptionResponse createTestByCourse(@RequestBody Map map, HttpSession session){
        String courseId = (String) map.get("courseId");
        String name = (String) map.get("name");
        Double testScore = 0.0;
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        List<String> questionIds = (List<String>) map.get("questionIds");
        List<QuestionBean> questionBeans = libraryService.queryQuestionByIds(teacher.getId(), questionIds);
        List<TestBodyBean> testBodyBeans = new ArrayList<>();
        List<TestRealAnswerBean> realAnswerBeans = new ArrayList<>();
        for (QuestionBean questionBean : questionBeans) {
            TestBodyBean testBody = new TestBodyBean();
            String question = questionBean.getQuestion();
            Integer type = questionBean.getType();
            Double score = questionBean.getScore();
            Integer maxNum = questionBean.getMaxNum();
            testScore += score;
            String answer = questionBean.getAnswer();
            List<AnswerBean> answerBeans = JSONArray.parseArray(answer, AnswerBean.class);
            testBody.setQuestion(question);
            testBody.setType(type);
            testBody.setScore(score);
            testBody.setMaxNum(maxNum);
            testBody.setAnswer(answerBeans);
            testBodyBeans.add(testBody);
            String realAnswer = questionBean.getRealAnswer();
            List<String> list = JSON.parseArray(realAnswer, String.class);
            TestRealAnswerBean testRealAnswerBean = new TestRealAnswerBean();
            testRealAnswerBean.setRealAnswer(list);
            realAnswerBeans.add(testRealAnswerBean);
        }
        Object testBody = JSON.toJSON(testBodyBeans);
        Object realAnswer = JSON.toJSON(realAnswerBeans);
        testService.addTestByCourse(name,testBody.toString(),testScore,courseId,teacher.getId(),realAnswer.toString());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("创建成功");
        return exceptionResponse;
    }

    /**
    * @Description: 获取试卷列表
    * @Param: [pageNum, pageSize, courseId, status, testId, testName, score, date, session]
    * @return: com.github.pagehelper.PageInfo<com.zut.interactivetools.bean.TestBean>
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/teacher/course/tests/{courseId}/{status}/{pageNum}")
    @ResponseBody
    public PageInfo<TestBean> getTestsByCourse(@PathVariable int pageNum, int pageSize,@PathVariable String courseId,@PathVariable Integer status,
                                               String testId, String testName, String score, String date, HttpSession session){
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        PageInfo<TestBean> testBeanPageInfo = testService.queryTestByCourse(pageNum, pageSize, teacher.getId(), courseId, status, testId, testName, score,date);
        return testBeanPageInfo;
    }

    /**
    * @Description: 获取试卷信息
    * @Param: [testId, courseId, session]
    * @return: com.zut.interactivetools.bean.TestBean
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/teacher/course/test/{testId}/{courseId}")
    @ResponseBody
    public TestBean getTestById(@PathVariable String testId,@PathVariable String courseId, HttpSession session){
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        TestBean testBean = testService.queryTestByTestId(testId, teacher.getId(), courseId);
        return testBean;
    }

    /**
    * @Description: 试卷预览
    * @Param: [map, session]
    * @return: com.zut.interactivetools.bean.TestBean
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @PostMapping("/teacher/course/test/preview")
    @ResponseBody
    public TestBean getPreviewTest(@RequestBody Map map, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        List<String> questionIds = (List<String>) map.get("questionIds");
        List<QuestionBean> questionBeans = libraryService.queryQuestionByIds(teacher.getId(), questionIds);
        List<TestBodyBean> testBodyBeans = new ArrayList<>();
        List<TestRealAnswerBean> realAnswerBeans = new ArrayList<>();
        for (QuestionBean questionBean : questionBeans) {
            TestBodyBean testBody = new TestBodyBean();
            String question = questionBean.getQuestion();
            Integer type = questionBean.getType();
            Double score = questionBean.getScore();
            Integer maxNum = questionBean.getMaxNum();
            String answer = questionBean.getAnswer();
            List<AnswerBean> answerBeans = JSONArray.parseArray(answer, AnswerBean.class);
            testBody.setQuestion(question);
            testBody.setType(type);
            testBody.setScore(score);
            testBody.setMaxNum(maxNum);
            testBody.setAnswer(answerBeans);
            testBodyBeans.add(testBody);
            String realAnswer = questionBean.getRealAnswer();
            List<String> list = JSON.parseArray(realAnswer, String.class);
            TestRealAnswerBean testRealAnswerBean = new TestRealAnswerBean();
            testRealAnswerBean.setRealAnswer(list);
            realAnswerBeans.add(testRealAnswerBean);
        }
        Object testBody = JSON.toJSON(testBodyBeans);
        Object realAnswer = JSON.toJSON(realAnswerBeans);
        TestBean testBean = new TestBean();
        testBean.setAnswer(realAnswer.toString());
        testBean.setBody(testBody.toString());
        return testBean;
    }

    /**
    * @Description: 删除试卷
    * @Param: [testId, courseId, session]
    * @return: com.zut.interactivetools.exception.ExceptionResponse
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @DeleteMapping("/teacher/course/test")
    @ResponseBody
    public ExceptionResponse delTestByCourse(String testId, String courseId, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        testService.delTestByCourse(testId, teacher.getId(), courseId);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("删除成功");
        return exceptionResponse;
    }

    /**
    * @Description: 发布试卷
    * @Param: [map, session]
    * @return: com.zut.interactivetools.exception.ExceptionResponse
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @Transactional
    @PostMapping("/teacher/course/test/testCourse")
    @ResponseBody
    public ExceptionResponse addTestCourseByCourse(@RequestBody Map map, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        String courseId = (String) map.get("courseId");
        String testId = (String) map.get("testId");
        String name = (String) map.get("name");
        Integer limit = Integer.parseInt(map.get("limit").toString());
        List<String> groupId = (List<String>) map.get("groupId");
        Integer submitType = Integer.parseInt(map.get("submitType").toString());
        Date submitTime = null;
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
        CourseBean courseBean = courseService.queryCourseInfoById(courseId, teacher.getId());
        TestBean testBean = testService.queryTestByTestId(testId, teacher.getId(), courseId);
        testService.addTestCourseByCourse(id, name, new Date(), testId, limit, testBean.getBody(), testBean.getAnswer(), teacher.getId(), courseId, g.substring(1),submitType);
        testService.addTestStudentByCourse(id, groupStudentBeans, teacher.getId(), courseId);
        if(submitType == 0){
            //企业微信推送
            TextCardBean textCardBean = TextCardCreateUtil.createSimpleTestTextCard(courseId,sids.toString(),courseBean.getName(),courseBean.getNumber(),teacher.getName(),id);
            wxWorkService.sendStuTextCard(textCardBean);
        }else if(submitType == 1){
            //企业微信推送
            SubmitTaskBean simpleTestSubmitTaskBean = SubmitTaskCreateUtil.createSimpleTestSubmitTaskBean(id, submitTime.getTime(), teacher.getId(), courseId,courseBean.getNumber(), sids.toString(), TaskEnum.SIMPLE_TEST, courseBean.getName(), teacher.getName());
            signCacheService.addSignCache(id,submitTime.getTime(),TaskEnum.SIMPLE_TEST,sids.toString(),teacher.getId(),courseId,courseBean.getNumber(),teacher.getName(),courseBean.getName());
            redisService.putTask(simpleTestSubmitTaskBean);
            if(limit != 0){
                SubmitTaskBean stopSimpleTestSubmitTaskBean = SubmitTaskCreateUtil.createStopSimpleTestSubmitTaskBean(simpleTestSubmitTaskBean,limit*1000);
                redisService.putTask(stopSimpleTestSubmitTaskBean);
            }

        }



        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setMsg(id);
        exceptionResponse.setDesc("发布成功");
        return exceptionResponse;
    }

    /**
    * @Description: 获取测试记录
    * @Param: [courseId, testCourseId, status, session]
    * @return: com.zut.interactivetools.bean.TestCourseBean
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/teacher/course/test/testCourse/content/{courseId}/{testCourseId}/{status}")
    @ResponseBody
    public TestCourseBean getTestCourseByTeacher(@PathVariable String courseId, @PathVariable String testCourseId,
                                                 @PathVariable Integer status, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        TestCourseBean testCourseBean = testService.queryTestCourseById(testCourseId, teacher.getId(), courseId, status);
        return testCourseBean;
    }

    /**
    * @Description: 获取测试记录列表
    * @Param: [pageNum, pageSize, courseId, status, id, name, date, session]
    * @return: com.github.pagehelper.PageInfo<com.zut.interactivetools.bean.TestCourseBean>
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/teacher/course/test/testCourses/{courseId}/{status}/{submitStatus}/{pageNum}")
    @ResponseBody
    public PageInfo<TestCourseBean> getTestCourseByCourse(@PathVariable int pageNum, int pageSize, @PathVariable String courseId,
                                                          @PathVariable Integer status,@PathVariable Integer submitStatus, String id,
                                                          String name, String date, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        PageInfo<TestCourseBean> testCourseBeanPageInfo = testService.queryTestCourseByCourse(pageNum, pageSize, teacher.getId(), courseId, status, id, name, date, submitStatus);
        return testCourseBeanPageInfo;
    }

    /**
    * @Description: 结束测试
    * @Param: [courseId, testCourseId, session]
    * @return: com.zut.interactivetools.exception.ExceptionResponse
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @PutMapping("/teacher/course/test/testCourse/out")
    @ResponseBody
    public ExceptionResponse outTestCourseById(String courseId, String testCourseId, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        testService.outTestCourseByCourse(testCourseId, teacher.getId(), courseId);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("结束成功");
        return exceptionResponse;
    }

    /**
    * @Description: 学生获取测试
    * @Param: [courseId, testCourseId, status, session]
    * @return: com.zut.interactivetools.bean.TestCourseBean
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/wxwork/student/test/testCourse/{courseId}/{testCourseId}/{status}")
    @ResponseBody
    public TestCourseBean getTestCourseByStudent(@PathVariable String courseId, @PathVariable String testCourseId, @PathVariable Integer status,
                                                 HttpSession session) {
        UserInforBean inforBean = (UserInforBean) session.getAttribute("UserInfo");
        TestCourseBean testCourseBean = testService.queryTestCourseByStudent(testCourseId, courseId, status, inforBean.getUserid());
        return testCourseBean;
    }

    /**
    * @Description: 检查测试是否正在进行
    * @Param: [testCourseId]
    * @return: java.lang.Boolean
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/wxwork/student/test/testCourse/isAlive/{testCourseId}")
    @ResponseBody
    public Boolean checkTestIsAliveByStudent(@PathVariable String testCourseId) {
        int i = testService.checkTestCourseAlive(testCourseId);
        return i > 0;
    }

    /**
    * @Description: 学生获取已答题的答案
    * @Param: [testCourseId, session]
    * @return: java.lang.Boolean
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/wxwork/student/test/testCourse/isAnswer/{testCourseId}")
    @ResponseBody
    public Boolean getIsAnswer(@PathVariable String testCourseId, HttpSession session) {
        UserInforBean inforBean = (UserInforBean) session.getAttribute("UserInfo");
        int i = testService.checkTestStudentIsAnswerByStudent(testCourseId, inforBean.getUserid());
        return i > 0;
    }

    /**
    * @Description: 学生提交测试
    * @Param: [map, session]
    * @return: com.zut.interactivetools.exception.ExceptionResponse
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @PutMapping("/wxwork/student/test/testCourse")
    @ResponseBody
    public ExceptionResponse submitTestByStudent(@RequestBody Map map, HttpSession session) {
        UserInforBean inforBean = (UserInforBean) session.getAttribute("UserInfo");
        String testCourseId = (String) map.get("testCourseId");
        String courseId = (String) map.get("courseId");
        Object o = map.get("answer");
        testService.updateTestStudentByStu(testCourseId, inforBean.getUserid(), courseId, new Date(), o);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("提交成功");
        return exceptionResponse;
    }

    /**
    * @Description: 学生获取测试记录
    * @Param: [testCourseId, session]
    * @return: com.zut.interactivetools.bean.TestStudentBean
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/wxwork/student/test/testStudent/{testCourseId}")
    @ResponseBody
    public TestStudentBean getTestStudentByStudent(@PathVariable String testCourseId, HttpSession session) {
        UserInforBean inforBean = (UserInforBean) session.getAttribute("UserInfo");
        TestStudentBean testStudentBean = testService.queryTestStudentAnswerScoreByStudent(testCourseId, inforBean.getUserid());
        return testStudentBean;

    }

    /**
    * @Description: 获取测试二维码
    * @Param: [courseId, testCourseId, session]
    * @return: org.springframework.http.ResponseEntity<byte[]>
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/teacher/test/testCourse/code/{courseId}/{testCourseId}")
    @ResponseBody
    public ResponseEntity<byte[]> getTestCode(@PathVariable String courseId, @PathVariable String testCourseId, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        utilService.checkTestCourse(testCourseId, courseId, teacher.getId());
        String info = WXWorkConfig.URL+"/wxwork/student/stuTest?testCourseId=" + testCourseId + "&courseId=" + courseId;
        ResponseEntity<byte[]> qrImage = qrService.getQRImage(info);
        return qrImage;
    }

    /**
    * @Description: 分析学生测试
    * @Param: [courseId, testCourseId, session]
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Integer>>
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/teacher/test/testCourse/analyse/{courseId}/{testCourseId}")
    @ResponseBody
    public List<Map<String, Integer>> analyseTestStudent(@PathVariable String courseId, @PathVariable String testCourseId, HttpSession session){
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        List<Map<String, Integer>> maps = testService.queryTestStudentAnswerByTeacherTest(testCourseId, courseId, teacher.getId());
        return maps;
    }

    /**
    * @Description: 获取学生测试记录
    * @Param: [courseId, testCourseId, status, session]
    * @return: java.util.List<com.zut.interactivetools.bean.TestStudentBean>
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/teacher/test/testStudent/{courseId}/{testCourseId}/{status}")
    @ResponseBody
    public List<TestStudentBean> getTestStudentByCourse(@PathVariable String courseId, @PathVariable String testCourseId, @PathVariable Integer status,
                                                        HttpSession session){
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        List<TestStudentBean> testStudentBeans = testService.queryTestStudentByTest(testCourseId, status, teacher.getId(), courseId);
        return testStudentBeans;
    }

    /**
    * @Description: 教师确认学生测试成绩
    * @Param: [map, session]
    * @return: com.zut.interactivetools.exception.ExceptionResponse
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @Transactional
    @PutMapping("/teacher/test/testStudent/confirm")
    @ResponseBody
    public ExceptionResponse confirmTestStudent(@RequestBody Map map,HttpSession session){
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        String courseId = (String) map.get("courseId");
        String testCourseId = (String) map.get("testCourseId");
        Object o = map.get("students");
        String s = JSON.toJSONString(o);
        List<TestStudentBean> testStudentBeans = JSON.parseArray(s, TestStudentBean.class);
        utilService.checkTestCourse(testCourseId, courseId, teacher.getId());
        testService.confirmTestCourseByCourse(testCourseId,teacher.getId(),courseId);
        for (TestStudentBean testStudentBean : testStudentBeans) {
            testService.updateTestStudentByCourse(testCourseId,testStudentBean.getStudent().getId(),testStudentBean.getGroup().getId(),
                    testStudentBean.getCourseScore(),teacher.getId(),courseId);
            groupService.updateScore(testStudentBean.getGroup().getId(),testStudentBean.getStudent().getId(),testStudentBean.getCourseScore(),teacher.getId());
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("确认成功");
        return exceptionResponse;
    }

    @Transactional
    @DeleteMapping("/teacher/test/cache/{courseId}/{signId}")
    @ResponseBody
    public ExceptionResponse cancelReserveSign(@PathVariable String courseId,@PathVariable String signId,HttpSession session){
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        SubmitTaskBean signCacheById = signCacheService.getSignCacheById(signId, teacher.getId(), courseId);
        TestCourseBean testCourseBean = testService.queryTestCourseById(signId, teacher.getId(), courseId, 0);
        SubmitTaskBean stopSimpleTestSubmitTaskBean = SubmitTaskCreateUtil.createStopSimpleTestSubmitTaskBean(signCacheById, testCourseBean.getLimitTime() * 1000);
        signCacheService.delCache(signId,teacher.getId(),courseId);
        testService.updateSubmitStatus(signId,courseId,teacher.getId());
        redisService.removeTask(signCacheById);
        redisService.removeTask(stopSimpleTestSubmitTaskBean);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("取消成功");
        return exceptionResponse;
    }
}
