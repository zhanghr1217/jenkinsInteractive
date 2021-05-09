package com.zut.interactivetools.util;

import com.alibaba.fastjson.JSON;
import com.zut.interactivetools.bean.SubmitTaskBean;
import com.zut.interactivetools.bean.TaskEnum;
import com.zut.interactivetools.wxwork.SubmitTask;

/**
 * @program: interactivetools
 * @description: submitTaskUtil
 * @author: zjj
 * @create: 2021-04-11 15:10
 **/
public class SubmitTaskCreateUtil {


    public static SubmitTaskBean createSimpleSignSubmitTaskBean(String id, long time, String teacherId, String courseId,String courseNum, String toUserId, TaskEnum taskEnum,String courseName,String teacherName){
        SubmitTaskBean submitTaskBean = new SubmitTaskBean();
        submitTaskBean.setId(id);
        submitTaskBean.setTaskTimestamp(time);
        submitTaskBean.setTeacherId(teacherId);
        submitTaskBean.setCourseId(courseId);
        submitTaskBean.setCourseNum(courseNum);
        submitTaskBean.setNeedSendUser(toUserId);
        submitTaskBean.setTaskEnum(taskEnum);
        submitTaskBean.setCourseName(courseName);
        submitTaskBean.setTeacherName(teacherName);
        return submitTaskBean;
    }

    public static SubmitTaskBean createSimpleTestSubmitTaskBean(String id, long time, String teacherId, String courseId,String courseNum, String toUserId, TaskEnum taskEnum,String courseName,String teacherName){
        SubmitTaskBean submitTestTaskBean = new SubmitTaskBean();
        submitTestTaskBean.setId(id);
        submitTestTaskBean.setTaskTimestamp(time);
        submitTestTaskBean.setTeacherId(teacherId);
        submitTestTaskBean.setCourseId(courseId);
        submitTestTaskBean.setCourseNum(courseNum);
        submitTestTaskBean.setNeedSendUser(toUserId);
        submitTestTaskBean.setTaskEnum(taskEnum);
        submitTestTaskBean.setCourseName(courseName);
        submitTestTaskBean.setTeacherName(teacherName);
        return submitTestTaskBean;
    }

    public static SubmitTaskBean createStopSimpleTestSubmitTaskBean(SubmitTaskBean submitTestTaskBean,long limit){
        SubmitTaskBean submitStopTestTaskBean = new SubmitTaskBean();
        submitStopTestTaskBean.setId(submitTestTaskBean.getId());
        submitStopTestTaskBean.setTaskTimestamp(submitTestTaskBean.getTaskTimestamp()+limit);
        submitStopTestTaskBean.setTeacherId(submitTestTaskBean.getTeacherId());
        submitStopTestTaskBean.setCourseId(submitTestTaskBean.getCourseId());
        submitStopTestTaskBean.setTaskEnum(TaskEnum.STOP_SIMPLE_TEST);

        return submitStopTestTaskBean;
    }


}
