package com.zut.interactivetools.service;

import com.zut.interactivetools.bean.LibraryBean;
import com.zut.interactivetools.bean.QuestionBean;
import com.zut.interactivetools.bean.SectionsBean;

import java.util.Date;
import java.util.List;

public interface LibraryService {
    List<LibraryBean> queryLibrariesByTeacher(String teacherId, Integer status);

    void addLibraryByTeacher(String teacherId, String name, String color);

    List<SectionsBean> querySectionsByLibrary(String libraryId, Integer status, String teacherId);

    void addSectionByLibrary(String name, String title, String libraryId, String teacherId);

    List<QuestionBean> queryQuestionBySection(String sectionId, String libraryId, String teacherId, Integer status);

    void addQuestion(String name, String question, String answer, String realAnswer, Integer type, String sectionId, String teacherId,
                     Double score, String libraryId, Integer maxNum);

    void delQuestion(String id, String libraryId, String teacherId);

    void updateQuestion(String id, String libraryId, String teacherId, String name, String question, String answer,
                        String realAnswer,Integer type, Double score, Integer maxNum);

    List<QuestionBean> queryQuestionByIds(String teacherId, List<String> qIds);
}
