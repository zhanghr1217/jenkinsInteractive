package com.zut.interactivetools.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.QuestionBean;
import com.zut.interactivetools.dao.QuestionDao;
import com.zut.interactivetools.exception.MyException;
import com.zut.interactivetools.service.QuestionService;
import com.zut.interactivetools.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionDao questionDao;
    @Autowired
    UtilService utilService;

    @Override
    public PageInfo<QuestionBean> queryQuestionsByCourse(int pageNum, int pageSize, String courseId, String teacherId,
                                                         Integer status, String questionId, String questionName, String question,
                                                         Integer type, Date createDate, Double score) {
        PageHelper.startPage(pageNum, pageSize);
        List<QuestionBean> questionBeans = questionDao.queryQuestionsByCourse(courseId, teacherId, status, questionId, questionName, question, type, createDate, score);
        return new PageInfo<>(questionBeans);
    }

    @Override
    public List<QuestionBean> queryQuestionByIds(String courseId, String teacherId, List<String> qIds) {
        return questionDao.queryQuestionByIds(courseId, teacherId, qIds);
    }

    @Override
    public void addQuestionByCourse(String name, String question, String answer, String realAnswer, Integer type,
                                    String courseId, String teacherId, Date createDate, Double score) {
        utilService.checkCourseAndTeacher(courseId, teacherId);
        String uuid = UUIDUtil.getUUID();
        int i = questionDao.addQuestionByCourse(uuid, name, question, answer, realAnswer, type, courseId, teacherId, createDate, score);
        if(i<1){
            throw new MyException(501,"addQuestionByCourse","添加失败");
        }

    }

    @Override
    public void delQuestionByCourse(String id, String courseId, String teacherId) {
//        utilService.checkCourseAndTeacher(courseId, teacherId);
        int i = questionDao.delQuestionByCourse(id, courseId, teacherId);
        if(i<1){
            throw new MyException(502,"delQuestionByCourse","删除失败");
        }
    }

    @Override
    public void updateQuestionByCourse(String id, String courseId, String teacherId, String name, String question, String answer,
                                       String realAnswer, Double score) {
//        utilService.checkCourseAndTeacher(courseId, teacherId);
        int i = questionDao.updateQuestionByCourse(id, courseId, teacherId, name, question, answer, realAnswer, score);
        if(i<1){
            throw new MyException(503,"updateQuestionByCourse","更新失败");
        }
    }
}
