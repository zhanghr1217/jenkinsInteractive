package com.zut.interactivetools.service.impl;

import com.zut.interactivetools.bean.TermBean;
import com.zut.interactivetools.dao.TermDao;
import com.zut.interactivetools.exception.MyException;
import com.zut.interactivetools.service.TermService;
import com.zut.interactivetools.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermServiceImpl implements TermService {

    @Autowired
    TermDao termDao;

    @Override
    public List<TermBean> queryAllTerm() {
        return termDao.queryAllTerm();
    }

    @Override
    public TermBean queryNowTerm() {
        return termDao.queryNowTerm();
    }

    @Override
    public String queryTermIdByCourse(String courseId, String teacherId) {
        return termDao.queryTermIdByCourse(courseId, teacherId);
    }

    @Override
    public void setNowTerm(String id) {
        int i = termDao.setNowTerm(id);
        if (i < 1) {
            throw new MyException(503, "setNowTerm", "更新失败");
        }
        int i1 = termDao.outTerm(id);
        if (i1 < 1) {
            throw new MyException(503, "outTerm", "更新失败");
        }
    }

    @Override
    public void addTerm(String name, Integer status) {
        String uuid = UUIDUtil.getUUID();
        int i = termDao.addTerm(uuid, name, status);
        if (i < 1) {
            throw new MyException(501, "addTerm", "添加失败");
        }
    }


}
