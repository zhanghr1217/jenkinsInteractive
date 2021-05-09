package com.zut.interactivetools.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zut.interactivetools.bean.TeacherBean;
import com.zut.interactivetools.bean.UserBean;
import com.zut.interactivetools.dao.UserDao;
import com.zut.interactivetools.exception.MyException;
import com.zut.interactivetools.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public PageInfo<TeacherBean> queryAllTeacher(int pageNum, int pageSize, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<TeacherBean> teacherBeans = userDao.queryAllTeacher(status);
        PageInfo<TeacherBean> teacherBeanPageInfo = new PageInfo<>(teacherBeans);
        return teacherBeanPageInfo;
    }

    @Override
    public TeacherBean loginTeacher(String id, String password) {
        TeacherBean teacherBean = userDao.loginTeacher(id, password);
        return teacherBean;
    }

    @Override
    public TeacherBean queryTeacherInfo(String id) {
        return userDao.queryTeacherInfo(id);
    }

    @Override
    public void addTeacherUser(List<TeacherBean> teacherBeans) {
        TeacherBean teacherBean = null;
        List<UserBean> userBeans = null;
        for (int i = 0; i < teacherBeans.size(); i++) {
            teacherBean = teacherBeans.get(i);
            UserBean userBean = new UserBean();
            userBean.setId(teacherBean.getId());
            userBean.setPassword("123456");
            userBeans.add(userBean);
            int res = userDao.checkTeacher(teacherBean.getId());
            if(res<1){
                throw new MyException(501,"第"+(i+1)+"行 id: "+teacherBean.getId()+" 已存在","添加失败");
            }
        }
        if(userBeans!=null){
            int i = userDao.addTeacher(teacherBeans);
            if( i < 1 ){
                throw new MyException(501,"addTeacher","添加失败");
            }
            int res = userDao.addTeacherUser(userBeans);
            if(res<1){
                throw new MyException(501,"addUser","添加失败");
            }
        }

    }


    @Override
    public void delTeacher(String id) {
        int i = userDao.delTeacher(id);
        if(i<1){
            throw new MyException(502,"delTeacher","删除失败");
        }
        int i1 = userDao.delUser(id);
        if(i1<1){
            throw new MyException(502,"delUser","删除失败");
        }
    }


    @Override
    public void updateTeacher(String id, String name, Integer gender, Date birthday, String tel, String head) {
        int i = userDao.updateTeacher(id, name, gender, birthday, tel, head);
        if(i<1){
            throw new MyException(503,"updateTeacher","更新失败");
        }
    }

    @Override
    public void updatePassword(String id, String oldPassword, String newPassword) {
        int i = userDao.updatePassword(id, oldPassword, newPassword);
        if(i<1){
            throw new MyException(503,"updatePassword","更新失败");
        }
    }

    @Override
    public int updateTeacherWXWork(String id, String name, Integer gender, String head) {
        int i = userDao.updateTeacherWXWork(id, name, gender, head);
        return i;
    }
}
