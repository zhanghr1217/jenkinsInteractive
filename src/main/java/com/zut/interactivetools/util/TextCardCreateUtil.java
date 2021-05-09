package com.zut.interactivetools.util;

import com.zut.interactivetools.bean.TextCardBean;
import com.zut.interactivetools.bean.TextCardInfoBean;
import com.zut.interactivetools.config.WXWorkConfig;

import java.util.Date;

/**
 * @program: interactivetools
 * @description: TextCard
 * @author: zjj
 * @create: 2021-04-11 14:23
 **/
public class TextCardCreateUtil {

    public static TextCardBean createSimpleCodeSignTextCard(String toUserId,String courseName,String courseNum,String teacherName){
        TextCardBean simpleCodeSignTextCard = new TextCardBean();
        TextCardInfoBean simpleCodeSignTextCardInfo = new TextCardInfoBean();
        simpleCodeSignTextCard.setAgentid(Integer.parseInt(WXWorkConfig.AGENT_ID_STU));
        simpleCodeSignTextCardInfo.setTitle("签到通知");
        simpleCodeSignTextCardInfo.setDescription("<div class=\"gray\">" + new Date().toString() + "</div> <div class=\"normal\">课程名：" + courseName + "课程号：" +
                courseNum + "教师：" + teacherName + "</div><div class=\"highlight\">请尽快签到</div>");
        simpleCodeSignTextCard.setTouser(toUserId);
        simpleCodeSignTextCardInfo.setUrl(WXWorkConfig.URL+"/wxwork/student/qr");
        simpleCodeSignTextCardInfo.setBtntxt("点击签到");
        simpleCodeSignTextCard.setTextCard(simpleCodeSignTextCardInfo);
        return simpleCodeSignTextCard;
    }

    public static TextCardBean createSimpleLocationSignTextCard(String toUserId,String courseName,String courseNum,String teacherName,String id){
        TextCardBean simpleLocationSignTextCard = new TextCardBean();
        TextCardInfoBean simpleLocationSignTextCardInfo = new TextCardInfoBean();
        simpleLocationSignTextCard.setAgentid(Integer.parseInt(WXWorkConfig.AGENT_ID_STU));
        simpleLocationSignTextCardInfo.setTitle("签到通知");
        simpleLocationSignTextCardInfo.setDescription("<div class=\"gray\">" + new Date().toString() + "</div> <div class=\"normal\">课程名：" + courseName + "课程号：" +
                courseNum + "教师：" + teacherName + "</div><div class=\"highlight\">请尽快签到</div>");
        simpleLocationSignTextCard.setTouser(toUserId);
        simpleLocationSignTextCardInfo.setUrl(WXWorkConfig.URL+"/wxwork/student/location?signId=" + id + "&type=2");
        simpleLocationSignTextCardInfo.setBtntxt("点击签到");
        simpleLocationSignTextCard.setTextCard(simpleLocationSignTextCardInfo);
        return simpleLocationSignTextCard;
    }

    public static TextCardBean createSimpleTestTextCard(String courseId,String toUserId,String courseName,String courseNum,String teacherName,String id){
        TextCardBean simpleTestTextCard = new TextCardBean();
        TextCardInfoBean simpleTestTextCardInfo = new TextCardInfoBean();
        simpleTestTextCard.setAgentid(Integer.parseInt(WXWorkConfig.AGENT_ID_STU));
        simpleTestTextCardInfo.setTitle("测试通知");
        simpleTestTextCardInfo.setDescription("<div class=\"gray\">" + new Date().toString() + "</div> <div class=\"normal\">课程名：" + courseName + "课程号：" +
                courseNum + "教师：" + teacherName + "</div><div class=\"highlight\">请尽快参与测试</div>");
        simpleTestTextCard.setTouser(toUserId);
        simpleTestTextCardInfo.setUrl(WXWorkConfig.URL+"/wxwork/student/stuTest?testCourseId=" + id + "&courseId=" + courseId);
        simpleTestTextCardInfo.setBtntxt("点击前往测验");
        simpleTestTextCard.setTextCard(simpleTestTextCardInfo);
        return simpleTestTextCard;
    }
}
