package com.zut.interactivetools.dao;

import com.zut.interactivetools.bean.QuestionBean;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface QuestionDao {
    List<QuestionBean> queryQuestionsByCourse(@Param("course_id") String courseId, @Param("teacher_id") String teacherId,
                                              @Param("status") Integer status, @Param("question_id") String questionId, @Param("question_name") String questionName,
                                              @Param("question") String question, @Param("type") Integer type, @Param("create_date") Date createDate,
                                              @Param("score") Double score);

    List<QuestionBean> queryQuestionByIds(@Param("course_id") String courseId, @Param("teacher_id") String teacherId, List<String> qIds);

    int addQuestionByCourse(@Param("id") String id, @Param("name") String name, @Param("question") String question, @Param("answer") String answer,
                            @Param("real_answer") String realAnswer, @Param("type") Integer type, @Param("course_id") String courseId,
                            @Param("teacher_id") String teacherId, @Param("create_date") Date createDate, @Param("score") Double score);

    int delQuestionByCourse(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId);

    int updateQuestionByCourse(@Param("id") String id, @Param("course_id") String courseId, @Param("teacher_id") String teacherId,
                               @Param("name") String name, @Param("question") String question, @Param("answer") String answer,
                               @Param("real_answer") String realAnswer, @Param("score") Double score);
}
