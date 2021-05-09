package com.zut.interactivetools.wxwork;

import com.alibaba.fastjson.JSON;
import com.zut.interactivetools.bean.SubmitTaskBean;
import com.zut.interactivetools.bean.TaskEnum;
import com.zut.interactivetools.bean.TextCardBean;
import com.zut.interactivetools.bean.TextCardInfoBean;
import com.zut.interactivetools.config.WXWorkConfig;
import com.zut.interactivetools.service.SignCacheService;
import com.zut.interactivetools.service.SignService;
import com.zut.interactivetools.service.TestService;
import com.zut.interactivetools.service.impl.RedisService;
import com.zut.interactivetools.service.impl.WXWorkService;
import com.zut.interactivetools.util.TextCardCreateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @program: interactivetools_integration
 * @description: 定时读取任务
 * @author: zjj
 * @create: 2021-02-06 12:55
 **/


@Component
public class SubmitTask {

    @Autowired
    RedisService redisService;
    @Autowired
    SignService signService;
    @Autowired
    TestService testService;
    @Autowired
    SignCacheService signCacheService;
    @Autowired
    WXWorkService wxWorkService;

    public String getMsgModel(String courseName,String teacherName){
        return "您有一门课程《"+courseName+"》（"+teacherName+"）正在考勤签到，点击进行扫码签到。";
    }

    @Async
    @Scheduled(fixedRate = 20*1000)
    public void submitTask(){
        long now = new Date().getTime();
        List<SubmitTaskBean> task = redisService.getTask(now);
        redisService.removeTask(now);
        for (SubmitTaskBean submitTaskBean : task) {
            if(submitTaskBean.getTaskEnum() == TaskEnum.CODE_SIGN){

//                List<String> list = new ArrayList<>();
//                list.add("201708024226");
                TextCardBean simpleCodeSignTextCard = TextCardCreateUtil.createSimpleCodeSignTextCard(submitTaskBean.getNeedSendUser(), submitTaskBean.getCourseName(), submitTaskBean.getCourseNum(), submitTaskBean.getTeacherName());
                wxWorkService.sendStuTextCard(simpleCodeSignTextCard);
                signService.updateSubmitStatus(submitTaskBean.getId(), submitTaskBean.getCourseId(), submitTaskBean.getTeacherId());
                signCacheService.delCache(submitTaskBean.getId(),submitTaskBean.getTeacherId(),submitTaskBean.getCourseId());
            }else if(submitTaskBean.getTaskEnum() == TaskEnum.LOCATION_SIGN){

//                List<String> list = new ArrayList<>();
//                list.add("201708024226");
                TextCardBean simpleCodeSignTextCard = TextCardCreateUtil.createSimpleLocationSignTextCard(submitTaskBean.getNeedSendUser(), submitTaskBean.getCourseName(), submitTaskBean.getCourseNum(), submitTaskBean.getTeacherName(),submitTaskBean.getId());
                wxWorkService.sendStuTextCard(simpleCodeSignTextCard);
                signService.updateSubmitStatus(submitTaskBean.getId(), submitTaskBean.getCourseId(), submitTaskBean.getTeacherId());
                signCacheService.delCache(submitTaskBean.getId(),submitTaskBean.getTeacherId(),submitTaskBean.getCourseId());
            }else if(submitTaskBean.getTaskEnum() == TaskEnum.SIMPLE_TEST){

//                List<String> list = new ArrayList<>();
//                list.add("201708024226");
                TextCardBean simpleTestTextCard = TextCardCreateUtil.createSimpleTestTextCard(submitTaskBean.getCourseId(),submitTaskBean.getNeedSendUser(), submitTaskBean.getCourseName(), submitTaskBean.getCourseNum(), submitTaskBean.getTeacherName(),submitTaskBean.getId());
                wxWorkService.sendStuTextCard(simpleTestTextCard);
                testService.updateSubmitStatus(submitTaskBean.getId(), submitTaskBean.getCourseId(), submitTaskBean.getTeacherId());
                signCacheService.delCache(submitTaskBean.getId(),submitTaskBean.getTeacherId(),submitTaskBean.getCourseId());
            }else if(submitTaskBean.getTaskEnum() == TaskEnum.STOP_SIMPLE_TEST){
                testService.outTestCourseByCourse(submitTaskBean.getId(),submitTaskBean.getTeacherId(),submitTaskBean.getCourseId());

            }
        }
    }
}
