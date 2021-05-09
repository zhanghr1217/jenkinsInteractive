package com.zut.interactivetools.dao;

import com.zut.interactivetools.bean.LibraryBean;
import com.zut.interactivetools.bean.QuestionBean;
import com.zut.interactivetools.bean.SectionsBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

public interface LibraryDao {
    List<LibraryBean> queryLibrariesByTeacher(@Param("teacher_id") String teacherId, @Param("status") Integer status);

    int addLibraryByTeacher(@Param("id") String id, @Param("teacher_id") String teacherId, @Param("name") String name,
                            @Param("create_date") Date createDate, @Param("color") String color);

    List<SectionsBean> querySectionsByLibrary(@Param("library_id") String libraryId, @Param("status") Integer status);

    int addSectionByLibrary(@Param("id") String id, @Param("name") String name, @Param("title") String title,
                            @Param("library_id") String libraryId, @Param("create_date") Date createDate);

    int checkLibrary(@Param("id") String libraryId, @Param("teacher_id") String teacherId);

    int checkSection(@Param("id") String sectionId, @Param("library_id") String libraryId);

    List<QuestionBean> queryQuestionBySection(@Param("section_id") String sectionId, @Param("library_id") String libraryId,
                                              @Param("teacher_id") String teacherId, @Param("status") Integer status);

    int addQuestion(@Param("id") String id, @Param("name") String name, @Param("question") String question, @Param("answer") String answer,
                    @Param("real_answer") String realAnswer,@Param("type") Integer type, @Param("section_id") String sectionId,
                    @Param("teacher_id") String teacherId, @Param("create_date") Date createDate, @Param("score") Double score,
                    @Param("library_id") String libraryId, @Param("max_num") Integer maxNum);

    int delQuestion(@Param("id") String id, @Param("library_id") String libraryId, @Param("teacher_id") String teacherId);

    int updateQuestion(@Param("id") String id, @Param("library_id") String libraryId, @Param("teacher_id") String teacherId,
                       @Param("name") String name, @Param("question") String question, @Param("answer") String answer,
                       @Param("real_answer") String realAnswer,@Param("type") Integer type, @Param("score") Double score, @Param("max_num") Integer maxNum);

    int querySectionsSumByLibrary(@Param("library_id") String libraryId);

    int queryQuestionsSumByLibrary(@Param("library_id") String libraryId);

    List<QuestionBean> queryQuestionByIds(@Param("teacher_id") String teacherId, List<String> qIds);


}
