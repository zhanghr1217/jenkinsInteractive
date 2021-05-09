package com.zut.interactivetools.dao;

import com.zut.interactivetools.bean.TermBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface TermDao {
    List<TermBean> queryAllTerm();

    TermBean queryNowTerm();

    String queryTermIdByCourse(@Param("course_id") String courseId, @Param("teacher_id") String teacherId);

    int setNowTerm(@Param("id") String id);

    int addTerm(@Param("id") String id, @Param("name") String name, @Param("status") Integer status);

    int outTerm(@Param("id") String id);
}
