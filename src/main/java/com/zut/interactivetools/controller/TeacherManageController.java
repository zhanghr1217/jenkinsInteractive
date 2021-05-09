package com.zut.interactivetools.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.*;
import com.zut.interactivetools.config.WXWorkConfig;
import com.zut.interactivetools.exception.ExceptionResponse;
import com.zut.interactivetools.exception.MyException;
import com.zut.interactivetools.service.*;
import com.zut.interactivetools.service.impl.UtilService;
import com.zut.interactivetools.service.impl.WXWorkService;
import com.zut.interactivetools.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class TeacherManageController {

    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @Autowired
    TermService termService;
    @Autowired
    GroupService groupService;
    @Autowired
    StudentService studentService;
    @Autowired
    SignService signService;
    @Autowired
    WXWorkService wxWorkService;
    @Autowired
    DiscussService discussService;
    @Autowired
    LibraryService libraryService;
    @Autowired
    UtilService utilService;
    @Autowired
    LotteryService lotteryService;
    @Autowired
    TestService testService;

    private String uploadImagesPath = WXWorkConfig.uploadImagesPath;
    private String uploadImagesPathMap = WXWorkConfig.uploadImagesPathMap;


    @GetMapping("/teacher/term/alive")
    @ResponseBody
    public TermBean getAliveTerm(){
        TermBean termBean = termService.queryNowTerm();
        return termBean;
    }

    /**
     * @Description: 教师登录
     * @Param: [id, password, model, session]
     * @return: java.lang.String
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @PostMapping("/teacher/login")
    public String teacherLogin(String id, String password, Model model, HttpSession session) {
        TeacherBean teacher = userService.loginTeacher(id, password);
        if (teacher != null) {
            UserBean user = new UserBean();
            user.setId(teacher.getId());
            user.setRole(0);
            session.setAttribute("user", user);
            session.setAttribute("teacher", teacher);
            return "redirect:/index";
        } else {
            model.addAttribute("error", "账号或密码错误!");
            return "login";
        }
    }

    /**
     * @Description: 教师登出
     * @Param: [session]
     * @return: java.lang.String
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @GetMapping("/teacher/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("teacher");
        session.removeAttribute("user");
        return "redirect:/login";
    }

    /**
     * @Description: 教师修改密码
     * @Param: [oldPassword, newPassword, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @PutMapping("/teacher/password")
    @ResponseBody
    public ExceptionResponse updatePassword(String oldPassword, String newPassword, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        userService.updatePassword(teacher.getId(), oldPassword, newPassword);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("修改成功");
        return exceptionResponse;
    }

    /**
     * @Description: 获取教师信息
     * @Param: [session]
     * @return: com.zut.interactivetools.bean.TeacherBean
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @GetMapping("/teacher/info")
    @ResponseBody
    public TeacherBean getTeacherInfo(HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        TeacherBean teacherBean = userService.queryTeacherInfo(teacher.getId());
        return teacherBean;
    }

    /**
     * @Description: 获取教师历史课程信息
     * @Param: [termStatus, status, session]
     * @return: java.util.List<com.zut.interactivetools.bean.CourseBean>
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @GetMapping("/teacher/courses/{termStatus}/{status}")
    @ResponseBody
    public List<CourseBean> getNowAliveCoursesByTeacher(@PathVariable Integer termStatus, @PathVariable Integer status, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        System.out.println(teacher);
        List<CourseBean> courseBeans = courseService.queryCourseByTeacher(teacher.getId(), termStatus, status);
        System.out.println(courseBeans);
        return courseBeans;
    }

    /**
     * @Description: 获取教师课程信息
     * @Param: [courseId, session]
     * @return: com.zut.interactivetools.bean.CourseBean
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @GetMapping("/teacher/course/{courseId}")
    @ResponseBody
    public CourseBean getCourseInfoByCourse(@PathVariable String courseId, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        CourseBean courseBean = courseService.queryCourseInfoById(courseId, teacher.getId());
        return courseBean;
    }

    /**
     * @Description: 删除课程
     * @Param: [courseId, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @DeleteMapping("/teacher/course/{courseId}")
    @ResponseBody
    public ExceptionResponse delCourseById(@PathVariable String courseId, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        courseService.delCourseByTeacher(courseId, teacher.getId());
        ExceptionResponse responseBean = new ExceptionResponse();
        responseBean.setCode(200);
        responseBean.setDesc("删除成功");
        return responseBean;
    }

    /**
     * @Description: 获取课程人员
     * @Param: [courseId, status, studentId, studentName, className, majorName, instituteName, session]
     * @return: java.util.List<com.zut.interactivetools.bean.StudentBean>
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @GetMapping("/teacher/course/students/{courseId}/{status}")
    @ResponseBody
    public List<StudentBean> getStudentsByCourse(@PathVariable("courseId") String courseId, @PathVariable("status") Integer status, String studentId,
                                                 String studentName, String className, String majorName, String instituteName, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        //获取课程的小组信息，通过hashmap进行映射
        List<GroupBean> groupBeans = groupService.queryGroupMapByCourse(courseId, teacher.getId(), 0);
        HashMap<String, String> map = new HashMap<>();
        for (GroupBean groupBean : groupBeans) {
            map.put(groupBean.getId(), groupBean.getName());
        }
        List<StudentBean> studentBeans = studentService.queryStudentsByCourse(courseId, status, studentId, studentName, className, majorName, instituteName, teacher.getId());
        //学生信息的分组信息(gid1|gid2...)通过hashmap得到分组信息
        for (StudentBean studentBean : studentBeans) {
            String groupId = studentBean.getGroupId();
            List<String> gname = new ArrayList<>();
            if (groupId != null && !"".equals(groupId)) {
                String[] fields = groupId.split("\\|");
                String g;
                for (String field : fields) {
                    if ((g = map.get(field)) != null) {
                        gname.add(g);
                    }
                }
                studentBean.setGroupName(gname);
            } else {
                studentBean.setGroupName(gname);
            }

        }
        return studentBeans;
    }

    /**
     * @Description: 添加人员
     * @Param: [courseId, sids, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @Transactional
    @PostMapping("/teacher/course/students")
    @ResponseBody
    public ExceptionResponse addStudentToCourse(String courseId, String sids, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        //按行分割学号
        String[] split = sids.split("\n");
        List<String> list = new ArrayList<>();
        for (String s : split) {
            s = s.trim();
            if (!"".equals(s)) {
                list.add(s);
            }
        }
        studentService.addStudentToCourse(courseId, list, teacher.getId());
        ExceptionResponse responseBean = new ExceptionResponse();
        responseBean.setCode(200);
        responseBean.setDesc("修改成功");
        return responseBean;
    }

    /**
     * @Description: 删除人员
     * @Param: [courseId, studentId, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @Transactional
    @DeleteMapping("/teacher/course/student")
    @ResponseBody
    public ExceptionResponse delStudentByCourse(String courseId, String studentId, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        GroupStudentBean groupStudentBean = studentService.queryStudentIdGroupIdByCourseStuId(courseId, 0, studentId);
        //获取人员的分组信息(gid1|gid2...)并从对应小组中删除学生
        if (groupStudentBean.getGid() != null && !"".equals(groupStudentBean.getGid())) {
            String[] fields = groupStudentBean.getGid().split("\\|");
            for (String field : fields) {
                studentService.delStudentFromGroup(field, studentId, teacher.getId());
            }
        }
        studentService.delStudentByCourse(courseId, studentId, teacher.getId());
        ExceptionResponse responseBean = new ExceptionResponse();
        responseBean.setCode(200);
        responseBean.setDesc("修改成功");
        return responseBean;
    }

    /**
     * @Description: 将学生加入小组
     * @Param: [map, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @Transactional
    @PutMapping("/teacher/addStudentToGroup/")
    @ResponseBody
    public ExceptionResponse addStudentToGroupByCourse(@RequestBody Map map, HttpSession session) {
        String courseId = (String) map.get("courseId");
        String groupId = (String) map.get("groupId");
        List<StudentBean> stus = (List<StudentBean>) map.get("students");
        ObjectMapper mapper = new ObjectMapper();
        List<StudentBean> list = mapper.convertValue(stus, new TypeReference<List<StudentBean>>() {
        });
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        List<String> sids = new ArrayList<>();
        List<String> sids2 = new ArrayList<>();
        for (StudentBean stu : list) {
            sids.add(stu.getId());
            sids2.add(stu.getId());
        }
        studentService.addStudentToGroup(groupId, sids, 0.0, teacher.getId());
        studentService.updateStudentGroupInCourseByCourse(courseId, sids2, groupId);
        ExceptionResponse responseBean = new ExceptionResponse();
        responseBean.setCode(200);
        responseBean.setDesc("修改成功");
        return responseBean;
    }

    /**
     * @Description: 创建课程
     * @Param: [name, number, file, session, model]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @PostMapping("/teacher/course")
    @ResponseBody
    public ExceptionResponse createCourse(String name, String number, MultipartFile file, HttpSession session, Model model) {

        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        if (name == null || "".equals(name)) {
            name = "未命名";
        }
        if (number == null || "".equals(number)) {
            number = "未知";
        }
        TermBean term = termService.queryNowTerm();
        String path = null;
        String uuid = UUIDUtil.getUUID();
        ExceptionResponse responseBean = new ExceptionResponse();
        if (file == null) {
            //默认图片
            path = uploadImagesPathMap + "/" + "default/courseHead/courseHead.png";
            courseService.addCourseByTeacher(uuid, number, name, teacher.getId(), term.getId(), path);
            responseBean.setCode(200);
            responseBean.setDesc("创建课程成功");
        } else {
            File dateFile = new File(teacher.getId() + "/" + uuid + "/" + "courseHead");
            String originalFile = file.getOriginalFilename();
            String suffix = originalFile.substring(originalFile.lastIndexOf("."));
            long time = new Date().getTime();
            File newFile = new File(uploadImagesPath + "/" + dateFile + "/" + time + "." + suffix);
            path = uploadImagesPathMap + "/" + teacher.getId() + "/" + uuid + "/" + "courseHead" + "/" + time + "." + suffix;
            if (!newFile.getParentFile().exists()) {
                newFile.getParentFile().mkdirs();
            }
            try {
                file.transferTo(newFile);
                courseService.addCourseByTeacher(uuid, number, name, teacher.getId(), term.getId(), path);
                responseBean.setCode(200);
                responseBean.setDesc("创建课程成功");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        }
        return responseBean;

    }

    /**
     * @Description: 更新课程
     * @Param: [courseId, name, number, file, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @PostMapping("/teacher/update/course")
    @ResponseBody
    public ExceptionResponse updateCourse(String courseId, String name, String number, MultipartFile file, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        if (name == null || "".equals(name)) {
            name = "未命名";
        }
        if (number == null || "".equals(number)) {
            number = "未知";
        }
        String path = null;
        ExceptionResponse responseBean = new ExceptionResponse();
        if (file == null) {
            courseService.updateCourseByTeacher(courseId, teacher.getId(), name, null, number);
            responseBean.setCode(200);
            responseBean.setDesc("修改成功");
        } else {
            File dateFile = new File(teacher.getId() + "/" + courseId + "/" + "courseHead");
            String originalFile = file.getOriginalFilename();
            String suffix = originalFile.substring(originalFile.lastIndexOf("."));
            long time = new Date().getTime();
            File newFile = new File(uploadImagesPath + "/" + dateFile + "/" + time + suffix);
            path = uploadImagesPathMap + "/" + teacher.getId() + "/" + courseId + "/" + "courseHead" + "/" + time + suffix;
            if (!newFile.getParentFile().exists()) {
                newFile.getParentFile().mkdirs();
            }
            try {
                file.transferTo(newFile);
                courseService.updateCourseByTeacher(courseId, teacher.getId(), name, path, number);
                responseBean.setCode(200);
                responseBean.setDesc("修改成功");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        }
        return responseBean;
    }

    /**
     * @Description: 获取小组信息
     * @Param: [courseId, status, session]
     * @return: java.util.List<com.zut.interactivetools.bean.GroupBean>
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @GetMapping("/teacher/groups/{courseId}/{status}")
    @ResponseBody
    public List<GroupBean> getGroupByCourse(@PathVariable("courseId") String courseId, @PathVariable("status") Integer status, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        List<GroupBean> groupBeans = groupService.queryGroupByCourse(courseId, teacher.getId(), status);
        //人员统计
        for (GroupBean groupBean : groupBeans) {
            int i = groupService.queryGroupSumByCourse(groupBean.getId());
            groupBean.setSum(i);
        }
        return groupBeans;
    }

    /**
     * @Description: 删除小组
     * @Param: [courseId, groupId, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @Transactional
    @DeleteMapping("/teacher/group/")
    @ResponseBody
    public ExceptionResponse delGroupByCourse(String courseId, String groupId, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        groupService.delGroupByCourse(groupId, teacher.getId(), courseId);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("删除成功");
        return exceptionResponse;

    }

    /**
     * @Description: 获取小组人员
     * @Param: [groupId, status, studentId, studentName, className, majorName, instituteName, session]
     * @return: java.util.List<com.zut.interactivetools.bean.StudentBean>
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @GetMapping("/teacher/group/students/{groupId}/{status}")
    @ResponseBody
    public List<StudentBean> queryStudentsByGroup(@PathVariable("groupId") String groupId, @PathVariable("status") Integer status,
                                                  String studentId, String studentName, String className, String majorName, String instituteName,
                                                  HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        List<StudentBean> studentBeans = studentService.queryStudentsByGroup(groupId, status, studentId, studentName, className, majorName, instituteName, teacher.getId());
        return studentBeans;

    }

    /**
     * @Description: 删除小组人员
     * @Param: [courseId, groupId, studentId, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @Transactional
    @DeleteMapping("/teacher/group/student")
    @ResponseBody
    public ExceptionResponse delStudentByGroup(String courseId, String groupId, String studentId, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        GroupStudentBean groupStudentBean = studentService.queryStudentIdGroupIdByCourseStuId(courseId, 0, studentId);
        if (groupStudentBean == null) {
            throw new MyException(508, "", "此人不存在");
        }
        String gid = groupStudentBean.getGid();
        StringBuilder builder = new StringBuilder();
        if (gid != null && !"".equals(gid)) {
            String[] fields = gid.split("\\|");
            for (String field : fields) {
                if (!field.equals(groupId)) {
                    builder.append("|").append(field);
                }
            }
            if (builder.toString().length() > 0) {
                groupStudentBean.setGid(builder.toString().substring(1));
            } else {
                groupStudentBean.setGid("");
            }

        }
        studentService.delStudentFromGroup(groupId, studentId, teacher.getId());
        studentService.updateStudentGroupInCourseByCourse(courseId, studentId, groupStudentBean.getGid());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("删除成功");
        return exceptionResponse;
    }

    /**
     * @Description: 获取小组的id和name的对应关系
     * @Param: [courseId, status, session]
     * @return: java.util.List<com.zut.interactivetools.bean.GroupBean>
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @GetMapping("/teacher/groupsMap/{courseId}/{status}")
    @ResponseBody
    public List<GroupBean> getGroupMapByCourse(@PathVariable("courseId") String courseId, @PathVariable("status") Integer status, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        List<GroupBean> groupBeans = groupService.queryGroupMapByCourse(courseId, teacher.getId(), status);
        return groupBeans;
    }

    /**
     * @Description: 创建小组
     * @Param: [courseId, name, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @PostMapping("/teacher/group")
    @ResponseBody
    public ExceptionResponse createGroupByCourse(String courseId, String name, HttpSession session) {
        ExceptionResponse responseBean = new ExceptionResponse();
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        String termId = termService.queryTermIdByCourse(courseId, teacher.getId());
        groupService.addGroupByCourse(name, teacher.getId(), courseId, termId);
        responseBean.setCode(200);
        responseBean.setDesc("创建小组成功");
        return responseBean;
    }

    /**
     * @Description: 小组更新成绩
     * @Param: [groupId, studentId, newScore, oldScore, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @PutMapping("/teacher/group/score")
    @ResponseBody
    public ExceptionResponse updateScoreByGroup(String groupId, String studentId, Double newScore, Double oldScore, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        groupService.updateScore(groupId, studentId, newScore - oldScore, teacher.getId());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("修改成功");
        return exceptionResponse;
    }

    /**
     * @Description: 获取题库
     * @Param: [status, session]
     * @return: java.util.List<com.zut.interactivetools.bean.LibraryBean>
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @GetMapping("/teacher/libraries/{status}")
    @ResponseBody
    public List<LibraryBean> getLibrariesByTeacher(@PathVariable Integer status, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        List<LibraryBean> libraryBeans = libraryService.queryLibrariesByTeacher(teacher.getId(), status);
        return libraryBeans;
    }

    /**
     * @Description: 添加题库
     * @Param: [name, color, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @PostMapping("/teacher/library")
    @ResponseBody
    public ExceptionResponse addLibraryByTeacher(String name, String color, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        libraryService.addLibraryByTeacher(teacher.getId(), name, color);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("创建成功");
        return exceptionResponse;
    }

    /**
     * @Description: 获取题库的章节和题目信息
     * @Param: [libraryId, sectionStatus, questionStatus, session]
     * @return: java.util.List<com.zut.interactivetools.bean.SectionsBean>
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @GetMapping("/teacher/library/question/{libraryId}/{sectionStatus}/{questionStatus}")
    @ResponseBody
    public List<SectionsBean> getSectionsAndQuestionsByTeacher(@PathVariable String libraryId, @PathVariable Integer sectionStatus,
                                                               @PathVariable Integer questionStatus, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        utilService.checkLibrary(libraryId, teacher.getId());
        List<SectionsBean> sectionsBeans = libraryService.querySectionsByLibrary(libraryId, sectionStatus, teacher.getId());
        if (sectionsBeans != null && sectionsBeans.size() > 0) {
            for (SectionsBean sectionsBean : sectionsBeans) {
                List<QuestionBean> questionBeans;
                questionBeans = libraryService.queryQuestionBySection(sectionsBean.getId(), libraryId, teacher.getId(), questionStatus);
                for (QuestionBean questionBean : questionBeans) {
                    if (questionBean.getAnswer() != null && !"".equals(questionBean.getAnswer())) {
                        Object parse = JSON.parse(questionBean.getAnswer());
                        List<AnswerBean> answerBeans = JSON.parseArray(parse.toString(), AnswerBean.class);
                        questionBean.setAnswerBeans(answerBeans);
                    }
                    if (questionBean.getRealAnswer() != null && !"".equals(questionBean.getRealAnswer())) {
                        Object parse1 = JSON.parse(questionBean.getRealAnswer());
                        List<String> real = JSON.parseArray(parse1.toString(), String.class);
                        questionBean.setRealBeans(real);
                    }
                }
                sectionsBean.setQuestions(questionBeans);
            }
        }
        return sectionsBeans;
    }

    /**
     * @Description: 添加章节
     * @Param: [libraryId, name, title, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @PostMapping("/teacher/library/section")
    @ResponseBody
    public ExceptionResponse addSectionByLibrary(String libraryId, String name, String title, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        libraryService.addSectionByLibrary(name, title, libraryId, teacher.getId());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("创建成功");
        return exceptionResponse;
    }

    /**
     * @Description: 添加问题
     * @Param: [map, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @PostMapping("/teacher/library/section/question")
    @ResponseBody
    public ExceptionResponse addQuestionByTeacher(@RequestBody Map map, HttpSession session) {
        String name = (String) map.get("name");
        String question = (String) map.get("question");
        List<String> answer = (List<String>) map.get("answer");
        List<String> realAnswer = (List<String>) map.get("realAnswer");
        String sectionId = (String) map.get("sectionId");
        Double score = Double.parseDouble(map.get("score").toString());
        Integer max_num = 0;
        String libraryId = (String) map.get("libraryId");
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        String answerJson = JSON.toJSONString(answer);
        String realJson = JSON.toJSONString(realAnswer);
        Integer type = 1;
        if (realAnswer.size() > 1) {
            type = 2;
        } else if (realAnswer.size() == 1) {
            type = 1;
        } else {
            type = 0;
        }
        if (type == 0) {
            max_num = Integer.parseInt(map.get("max_num").toString());
        }
        libraryService.addQuestion(name, question, answerJson, realJson, type, sectionId, teacher.getId(), score, libraryId, max_num);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("创建成功");
        return exceptionResponse;
    }

    /**
     * @Description: 更新问题
     * @Param: [map, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @PutMapping("/teacher/library/section/question")
    @ResponseBody
    public ExceptionResponse updateQuestionByTeacher(@RequestBody Map map, HttpSession session) {
        String name = (String) map.get("name");
        String question = (String) map.get("question");
        List<String> answer = (List<String>) map.get("answer");
        List<String> realAnswer = (List<String>) map.get("realAnswer");
        Double score = Double.parseDouble(map.get("score").toString());
        Integer max_num = 0;
        String libraryId = (String) map.get("libraryId");
        String questionId = (String) map.get("questionId");
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        String answerJson = JSON.toJSONString(answer);
        String realJson = JSON.toJSONString(realAnswer);
        Integer type = 1;
        if (realAnswer.size() > 1) {
            type = 2;
        } else if (realAnswer.size() == 1) {
            type = 1;
        } else {
            type = 0;
        }
        if (type == 0) {
            max_num = Integer.parseInt(map.get("max_num").toString());
        }
        libraryService.updateQuestion(questionId, libraryId, teacher.getId(), name, question, answerJson, realJson, type, score, max_num);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("修改成功");
        return exceptionResponse;
    }

    /**
     * @Description: 删除问题
     * @Param: [questionId, libraryId, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @DeleteMapping("/teacher/library/section/question")
    @ResponseBody
    public ExceptionResponse deleteQuestion(String questionId, String libraryId, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        libraryService.delQuestion(questionId, libraryId, teacher.getId());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("删除成功");
        return exceptionResponse;
    }

    /**
     * @Description: 查询抽人记录
     * @Param: [pageNum, pageSize, courseId, status, studentId, studentName, className, majorName, instituteName, createDate, session]
     * @return: com.github.pagehelper.PageInfo<com.zut.interactivetools.bean.LotteryBean>
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @GetMapping("/teacher/course/lotteries/{courseId}/{status}/{pageNum}")
    @ResponseBody
    public PageInfo<LotteryBean> queryLotteriesByCourse(@PathVariable int pageNum, int pageSize, @PathVariable String courseId,
                                                        @PathVariable Integer status, String studentId, String studentName,
                                                        String className, String majorName, String instituteName, String createDate,
                                                        HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        PageInfo<LotteryBean> lotteryBeanPageInfo = lotteryService.queryLotteryByCourse(pageNum, pageSize, teacher.getId(), courseId, status, studentId, studentName, className, majorName,
                instituteName, createDate);
        return lotteryBeanPageInfo;
    }

    /**
     * @Description: 添加抽人记录
     * @Param: [map, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @Transactional
    @PostMapping("/teacher/course/lottery")
    @ResponseBody
    public ExceptionResponse addLotteryByCourse(@RequestBody Map map, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        List<String> studentIds = (List<String>) map.get("studentIds");
        String courseId = (String) map.get("courseId");
        Date date = new Date();
        for (String studentId : studentIds) {
            int i = utilService.checkStudent(studentId);
            if (i < 1) {
                throw new MyException(504, "", "学号错误");
            }
            lotteryService.addLotteryByCourse(teacher.getId(), courseId, studentId, date);
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("添加成功");
        return exceptionResponse;
    }

    /**
     * @Description: 删除抽人记录
     * @Param: [id, courseId, session]
     * @return: com.zut.interactivetools.exception.ExceptionResponse
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @DeleteMapping("/teacher/course/lottery")
    @ResponseBody
    public ExceptionResponse deleteLotteryByCourse(String id, String courseId, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        lotteryService.delLotteryByCourse(id, teacher.getId(), courseId);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(200);
        exceptionResponse.setDesc("删除成功");
        return exceptionResponse;
    }

    /**
     * @Description: 获取抽入小组人员信息
     * @Param: [map, session]
     * @return: java.util.List<com.zut.interactivetools.bean.StudentBean>
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @PostMapping("/teacher/course/lottery/students/info")
    @ResponseBody
    public List<StudentBean> getLotteryStudentsByGroups(@RequestBody Map map, HttpSession session) {
        TeacherBean teacher = (TeacherBean) session.getAttribute("teacher");
        List<String> groupId = (List<String>) map.get("groupId");
        List<StudentBean> studentBeans = lotteryService.queryLotteryStudentInfoByGroup(groupId, teacher.getId());
        return studentBeans;
    }


}
