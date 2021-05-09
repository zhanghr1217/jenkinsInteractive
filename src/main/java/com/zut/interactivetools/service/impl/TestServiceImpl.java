package com.zut.interactivetools.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.*;
import com.zut.interactivetools.dao.TestDao;
import com.zut.interactivetools.exception.MyException;
import com.zut.interactivetools.service.GroupService;
import com.zut.interactivetools.service.StudentService;
import com.zut.interactivetools.service.TestService;
import com.zut.interactivetools.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.*;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestDao testDao;
    @Autowired
    UtilService utilService;
    @Autowired
    GroupService groupService;

    @Override
    public PageInfo<TestBean> queryTestByCourse(int pageNum, int pageSize, String teacherId, String courseId, Integer status,
                                                String testId, String testName, String score, String date) {
        PageHelper.startPage(pageNum, pageSize);
        List<TestBean> testBeans = testDao.queryTestByCourse(teacherId, courseId, status, testId, testName, score, date);
        return new PageInfo<>(testBeans);
    }

    @Override
    public void addTestByCourse(String name, String body, Double score, String courseId, String teacherId, String answer) {
        String uuid = UUIDUtil.getUUID();
        utilService.checkCourseAndTeacher(courseId, teacherId);
        int i = testDao.addTestByCourse(uuid, name, body, score, courseId, teacherId, answer, new Date());
        if (i < 1) {
            throw new MyException(501, "addTestByCourse", "添加失败");
        }
    }

    @Override
    public void delTestByCourse(String id, String teacherId, String courseId) {
//        utilService.checkCourseAndTeacher(courseId, teacherId);
        int i = testDao.delTestByCourse(id, teacherId, courseId);
        if (i < 1) {
            throw new MyException(502, "delTestByCourse", "删除失败");
        }
    }

    @Override
    public void updateTestByCourse(String id, String teacherId, String courseId, String name, String body, Double score, String answer) {
//        utilService.checkCourseAndTeacher(courseId, teacherId);
        int i = testDao.updateTestByCourse(id, teacherId, courseId, name, body, score, answer);
        if (i < 1) {
            throw new MyException(503, "updateTestByCourse", "更新失败");
        }
    }

    @Override
    public TestBean queryTestByTestId(String id, String teacherId, String courseId) {
        return testDao.queryTestByTestId(id, teacherId, courseId);
    }

    @Override
    public PageInfo<TestCourseBean> queryTestCourseByCourse(int pageNum, int pageSize, String teacherId, String courseId,
                                                            Integer status, String id, String name, String date,Integer submitStatus) {
        PageHelper.startPage(pageNum, pageSize);
        List<TestCourseBean> testCourseBeans = testDao.queryTestCourseByCourse(teacherId, courseId, status, id, name, date, submitStatus);
        List<GroupBean> groupBeans = groupService.queryGroupMapByCourseNoStatus(courseId, teacherId);
        HashMap<String, String> map = new HashMap<>();
        for (GroupBean groupBean : groupBeans) {
            map.put(groupBean.getId(), groupBean.getName());
        }
        for (TestCourseBean testCourseBean : testCourseBeans) {
            if (testCourseBean.getGroupId() != null && !"".equals(testCourseBean.getGroupId())) {
                String[] split = testCourseBean.getGroupId().split("\\|");
                List<String> gname = new ArrayList<>();
                String g = null;
                for (String s : split) {
                    if ((g = map.get(s)) != null) {
                        gname.add(g);
                    }
                }
                testCourseBean.setGroupName(gname);
            } else {
                testCourseBean.setGroupName(new ArrayList<>());
            }
        }
        return new PageInfo<>(testCourseBeans);
    }

    @Override
    public TestCourseBean queryTestCourseById(String id, String teacherId, String courseId, Integer status) {
        return testDao.queryTestCourseById(id, teacherId, courseId, status);
    }

    @Override
    public TestCourseBean queryTestCourseByStudent(String id, String courseId, Integer status, String studentId) {
        utilService.checkTestStudentByStudent(id, studentId);
        TestCourseBean testCourseBean = testDao.queryTestCourseByStudent(id, courseId, status);
        return testCourseBean;
    }

    @Override
    public void delTestCourseByCourse(String id, String teacherId, String courseId) {
//        utilService.checkCourseAndTeacher(courseId, teacherId);
        int i = testDao.delTestCourseByCourse(id, teacherId, courseId);
        if (i < 1) {
            throw new MyException(502, "delTestCourseByCourse", "删除失败");
        }
    }

    @Override
    public void updateTestCourseByCourse(String id, String teacherId, String courseId, String name) {
//        utilService.checkCourseAndTeacher(courseId, teacherId);
        int i = testDao.updateTestCourseByCourse(id, teacherId, courseId, name);
        if (i < 1) {
            throw new MyException(503, "updateTestCourseByCourse", "更新失败");
        }
    }

    @Override
    public void outTestCourseByCourse(String id, String teacherId, String courseId) {
//        utilService.checkTestCourse(id, teacherId, courseId);
        int i = testDao.outTestCourseByCourse(id, teacherId, courseId);
        if (i < 1) {
            throw new MyException(503, "outTestCourseByCourse", "更新失败");
        }
    }

    @Override
    public void confirmTestCourseByCourse(String id, String teacherId, String courseId) {
        int i = testDao.confirmTestCourseByCourse(id, teacherId, courseId);
        if (i < 1) {
            throw new MyException(503, "confirmTestCourseByCourse", "更新失败");
        }
    }

    @Override
    public void addTestCourseByCourse(String id, String name, Date createDate, String testId, Integer limit, String testBody, String testAnswer, String teacherId,
                                      String courseId, String groupId,Integer submitStatus) {
        utilService.checkTest(testId, courseId, teacherId);
        int i = testDao.addTestCourseByCourse(id, name, createDate, testId, limit, testBody, testAnswer, teacherId, courseId, groupId,submitStatus);
        if (i < 1) {
            throw new MyException(501, "addTestCourseByCourse", "添加失败");
        }


    }

    @Override
    public List<TestStudentBean> queryTestStudentByTest(String tId, Integer status, String teacherId, String courseId) {
        utilService.checkTestCourse(tId, courseId, teacherId);
        List<TestStudentBean> testStudentBeans = testDao.queryTestStudentByTest(tId, status);
        return testStudentBeans;
    }

    @Override
    public TestStudentBean queryTestStudentAnswerScoreByStudent(String tId, String studentId) {
        return testDao.queryTestStudentAnswerScoreByStudent(tId, studentId);
    }

    @Override
    public List<Map<String, Integer>> queryTestStudentAnswerByTeacherTest(String tId, String courseId, String teacherId) {
        utilService.checkTestCourse(tId, courseId, teacherId);
        List<String> list = testDao.queryTestStudentAnswerByTeacherTest(tId);
        TestCourseBean testCourseBean = testDao.queryTestCourseById(tId, teacherId, courseId, 0);
        List<TestBodyBean> testBodyBeans = JSON.parseArray(testCourseBean.getTestBody(), TestBodyBean.class);
        List<Map<String, Integer>> analys = new ArrayList<>();
        for (TestBodyBean testBodyBean : testBodyBeans) {
            List<AnswerBean> answer = testBodyBean.getAnswer();
            Map<String, Integer> map = new LinkedHashMap<>();
            for (AnswerBean answerBean : answer) {
                map.put(answerBean.getKey(), 0);
            }
            analys.add(map);
        }

        for (String s : list) {
            List<TestRealAnswerBean> testRealAnswerBeans = JSON.parseArray(s, TestRealAnswerBean.class);
            if(testRealAnswerBeans!=null){
                for (int i = 0; i < testRealAnswerBeans.size(); i++) {
                    TestRealAnswerBean testRealAnswerBean = testRealAnswerBeans.get(i);
                    for (String s1 : testRealAnswerBean.getRealAnswer()) {
                        Map<String, Integer> map = analys.get(i);
                        if (map.containsKey(s1)) {
                            map.put(s1, map.get(s1) + 1);
                        }
                    }
                }
            }
        }

        return analys;
    }

//    @Override
//    public TestStudentBean queryTestStudentById(String tId, Integer status, String studentId, Double courseScore) {
//        testDao.queryTestStudentById()
//        return ;
//    }

    @Override
    public void updateTestStudentByCourse(String tId, String studentId, String groupId, Double courseScore,
                                          String teacherId, String courseId) {
        int i = testDao.updateTestStudentByCourse(tId, studentId, groupId, courseScore);
        if (i < 1) {
            throw new MyException(503, "updateTestStudentByCourse", "更新失败");
        }
    }

    @Override
    public void delTestStudentByCourse(String tId, String studentId, String groupId, String teacherId, String courseId) {
        utilService.checkTestCourse(tId, courseId, teacherId);
        int i = testDao.delTestStudentByCourse(tId, studentId, groupId);
        if (i < 1) {
            throw new MyException(502, "delTestCourseByCourse", "删除失败");
        }

    }

    @Override
    public void addTestStudentByCourse(String tId, List<GroupStudentBean> list, String teacherId, String courseId) {
        utilService.checkTestCourse(tId, courseId, teacherId);
        for (GroupStudentBean groupStudentBean : list) {
            utilService.checkGroupStudent(groupStudentBean.getGid(), groupStudentBean.getSid());
        }
        int i = testDao.addTestStudentByCourse(tId, list);
        if (i < 1) {
            throw new MyException(501, "addTestStudentByCourse", "添加失败");
        }

    }

    @Override
    public void updateTestStudentByStu(String tId, String studentId, String courseId, Date subDate, Object answer) {
        utilService.checkTestCourseAlive(tId);
        utilService.checkTestStudentIsAnswerByStudent(tId, studentId);
        String s = JSON.toJSONString(answer);
        List<TestRealAnswerBean> realAnswerBeans = JSON.parseArray(s, TestRealAnswerBean.class);
        TestCourseBean testCourseBean = queryTestCourseByStudent(tId, courseId, 0, studentId);
        List<TestRealAnswerBean> realAnswerBeans1 = JSON.parseArray(testCourseBean.getTestAnswer(), TestRealAnswerBean.class);
        List<TestBodyBean> testBodyBeans = JSON.parseArray(testCourseBean.getTestBody(), TestBodyBean.class);
        Double score = 0.0;
        for (int i = 0; i < realAnswerBeans1.size(); i++) {
            if(testBodyBeans.get(i).getType()==0){
                score += testBodyBeans.get(i).getScore();
            }else {
                TestRealAnswerBean t1 = realAnswerBeans1.get(i);
                TestRealAnswerBean t2 = realAnswerBeans.get(i);
                List l1 = t1.getRealAnswer();
                List l2 = t2.getRealAnswer();
                l1.retainAll(l2);
                if (l1.size() == l2.size()) {
                    score += testBodyBeans.get(i).getScore();
                }
            }
        }
        int i = testDao.updateTestStudentByStu(tId, studentId, subDate, score, s);
        if (i < 1) {
            throw new MyException(503, "updateTestStudentByStu", "更新失败");
        }
    }

    @Override
    public int checkTestCourseAlive(String id) {
        return testDao.checkTestCourseAlive(id);
    }

    @Override
    public int checkTestStudentIsAnswerByStudent(String tId, String studentId) {
        return testDao.checkTestStudentIsAnswerByStudent(tId, studentId);
    }

    @Override
    public void updateSubmitStatus(String id, String courseId, String teacherId) {
        int i = testDao.updateSubmitStatus(id, courseId, teacherId);
        if (i < 1) {
            throw new MyException(503, "updateSubmitStatus", "修改失败");
        }
    }
}
