package com.zut.interactivetools.service.impl;

import com.zut.interactivetools.bean.LibraryBean;
import com.zut.interactivetools.bean.QuestionBean;
import com.zut.interactivetools.bean.SectionsBean;
import com.zut.interactivetools.dao.LibraryDao;
import com.zut.interactivetools.exception.MyException;
import com.zut.interactivetools.service.LibraryService;
import com.zut.interactivetools.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    LibraryDao libraryDao;
    @Autowired
    UtilService utilService;

    @Override
    public List<LibraryBean> queryLibrariesByTeacher(String teacherId, Integer status) {
        List<LibraryBean> libraryBeans = libraryDao.queryLibrariesByTeacher(teacherId, status);
        for (LibraryBean libraryBean : libraryBeans) {
            int i = libraryDao.querySectionsSumByLibrary(libraryBean.getId());
            libraryBean.setSectionSum(i);
            int i1 = libraryDao.queryQuestionsSumByLibrary(libraryBean.getId());
            libraryBean.setQuestionSum(i1);
        }
        return libraryBeans;
    }

    @Override
    public void addLibraryByTeacher(String teacherId, String name, String color) {
        String id = UUIDUtil.getUUID();
        int i = libraryDao.addLibraryByTeacher(id, teacherId, name, new Date(), color);
        if (i < 1) {
            throw new MyException(501, "addLibraryByTeacher", "添加失败");
        }
    }

    @Override
    public List<SectionsBean> querySectionsByLibrary(String libraryId, Integer status, String teacherId) {
        return libraryDao.querySectionsByLibrary(libraryId, status);
    }

    @Override
    public void addSectionByLibrary(String name, String title, String libraryId, String teacherId) {
        String id = UUIDUtil.getUUID();
        int i = libraryDao.addSectionByLibrary(id, name, title, libraryId, new Date());
        if (i < 1) {
            throw new MyException(501, "addSectionByLibrary", "添加失败");
        }
    }

    @Override
    public List<QuestionBean> queryQuestionBySection(String sectionId, String libraryId, String teacherId, Integer status) {
        return libraryDao.queryQuestionBySection(sectionId, libraryId, teacherId, status);
    }

    @Override
    public void addQuestion(String name, String question, String answer, String realAnswer, Integer type, String sectionId, String teacherId, Double score, String libraryId, Integer maxNum) {
        utilService.checkLibrary(libraryId, teacherId);
        utilService.checkSection(sectionId, libraryId);
        String id = UUIDUtil.getUUID();
        int i = libraryDao.addQuestion(id, name, question, answer, realAnswer, type, sectionId, teacherId, new Date(), score, libraryId, maxNum);
        if (i < 1) {
            throw new MyException(501, "addSectionByLibrary", "添加失败");
        }
    }

    @Override
    public void delQuestion(String id, String libraryId, String teacherId) {
        int i = libraryDao.delQuestion(id, libraryId, teacherId);
        if (i < 1) {
            throw new MyException(502, "delQuestion", "删除失败");
        }
    }

    @Override
    public void updateQuestion(String id, String libraryId, String teacherId, String name, String question, String answer,
                               String realAnswer, Integer type, Double score, Integer maxNum) {
        int i = libraryDao.updateQuestion(id, libraryId, teacherId, name, question, answer, realAnswer,type, score, maxNum);
        if (i < 1) {
            throw new MyException(503, "updateQuestion", "修改失败");
        }
    }

    @Override
    public List<QuestionBean> queryQuestionByIds(String teacherId, List<String> qIds) {
        return libraryDao.queryQuestionByIds(teacherId, qIds);
    }
}
