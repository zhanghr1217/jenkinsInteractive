package com.zut.interactivetools.service;

import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.QuestionBean;

import java.util.Date;
import java.util.List;

public interface QuestionService {
    PageInfo<QuestionBean> queryQuestionsByCourse(int pageNum, int pageSize, String courseId, String teacherId, Integer status, String questionId,
                                                  String questionName, String question, Integer type, Date createDate, Double score);

    List<QuestionBean> queryQuestionByIds(String courseId, String teacherId, List<String> qIds);

    void addQuestionByCourse(String name, String question, String answer, String realAnswer, Integer type, String courseId,
                             String teacherId, Date createDate, Double score);

    void delQuestionByCourse(String id, String courseId, String teacherId);

    void updateQuestionByCourse(String id, String courseId, String teacherId, String name, String question, String answer,
                                String realAnswer, Double score);
}
