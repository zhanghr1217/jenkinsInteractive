package com.zut.interactivetools.controller;

import com.alibaba.fastjson.JSONObject;
import com.zut.interactivetools.bean.*;
import com.zut.interactivetools.config.WXWorkConfig;
import com.zut.interactivetools.exception.ExceptionResponse;
import com.zut.interactivetools.service.StudentService;
import com.zut.interactivetools.service.UserService;
import com.zut.interactivetools.service.impl.JsApiService;
import com.zut.interactivetools.service.impl.UtilService;
import com.zut.interactivetools.service.impl.WXWorkService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

@Controller
public class WXWorkController {
    @Autowired
    JsApiService jsApiService;
    @Autowired
    WXWorkService wxWorkService;
    @Autowired
    StudentService studentService;
    @Autowired
    UtilService utilService;
    @Autowired
    UserService userService;

    /**
     * @Description: 企业微信网页授权
     * @Param: [url]
     * @return: com.zut.interactivetools.bean.WxConfigBean
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @GetMapping("/wxwork/authorize/wxconfig")
    @ResponseBody
    public WxConfigBean getJsSdkSignature(String url) {
        String noncestr = RandomStringUtils.randomAlphanumeric(16);
        long timestemp = new Date().getTime();
        String s = jsApiService.JsSdkSignature(noncestr, timestemp, url);
        WxConfigBean wxConfigBean = new WxConfigBean();
        wxConfigBean.setAppId(WXWorkConfig.CORP_ID);
        wxConfigBean.setNonceStr(noncestr);
        wxConfigBean.setTimestamp(timestemp);
        wxConfigBean.setSignature(s);
        return wxConfigBean;
    }

    @GetMapping("/wxwork/serverurl")
    @ResponseBody
    public String getServerUrl(){
        return WXWorkConfig.URL;
    }

    /**
     * @Description: 获取企业微信授权相关数据
     * @Param: []
     * @return: java.lang.String
     * @Author: zjj
     * @Date: 2021/1/5
     */
    @GetMapping(value = "/wxwork/authorize/getAuthorizePath")
    @ResponseBody
    public String getStuAuthorizePath() {
//        https://open.weixin.qq.com/connect/oauth2/authorize?
        // appid=CORPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
        String redirect_uri = WXWorkConfig.URL + "/wxwork/authorize/getCode";
        String json = null;
        try {
            redirect_uri = URLEncoder.encode(redirect_uri, "gbk");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("redirect_uri", redirect_uri);
            jsonObject.put("corp_id", WXWorkConfig.CORP_ID);
            json = jsonObject.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
    * @Description: 企业微信用户信息
    * @Param: [code, state, model, session]
    * @return: java.lang.String
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping(value = "/wxwork/authorize/getCode")
    public String getUserInfor(String code, String state, Model model, HttpSession session) {
//        String[] split = state.split("\\/");
//        System.out.println(Arrays.toString(split));
//        String type = split[0];
//        String id = split[1];
        UserInforBean inforBean = (UserInforBean) session.getAttribute("UserInfo");
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        if (inforBean == null) {
//        model.addAttribute("code",code);
            String userId = wxWorkService.getUserId(code);
            if (userId != null) {

                UserInforBean inforByUserId = wxWorkService.getUserInforByUserId(userId);
//            String thumb_avatar = inforByUserId.getThumb_avatar();
//            String name = inforByUserId.getName();
                int i = userService.updateTeacherWXWork(userId, inforByUserId.getName(), inforByUserId.getGender(), inforByUserId.getThumb_avatar());
                if(i>0){
                    TeacherBean teacherBean = new TeacherBean();
                    teacherBean.setId(userId);
                    teacherBean.setName(inforByUserId.getName());
                    UserBean userBean = new UserBean();
                    userBean.setId(userId);
                    session.setAttribute("teacher",teacherBean);
                    session.setAttribute("user",userBean);

                }else {
                    studentService.updateStudentInfo(userId, inforByUserId.getName(), inforByUserId.getThumb_avatar(), inforByUserId.getGender());
                }
//                String gbk = URLEncoder.encode(thumb_avatar, "gbk");
//                String name_gbk = URLEncoder.encode(name,"gbk");
//                System.out.println(gbk);
//                System.out.println(name_gbk);
//                inforByUserId.setThumb_avatar(gbk);
                session.setAttribute("UserInfo", inforByUserId);
//                session.setAttribute("thumb_avatar_gbk",gbk);
//                session.setAttribute("name_gbk",name_gbk);
                exceptionResponse.setCode(200);
                exceptionResponse.setMsg(inforByUserId.getThumb_avatar());
                exceptionResponse.setDesc("欢迎您，" + inforByUserId.getName());

            } else {
                exceptionResponse.setCode(500);
                exceptionResponse.setMsg("认证失败");

            }
        } else {
            exceptionResponse.setCode(200);
            exceptionResponse.setMsg(inforBean.getThumb_avatar());
            exceptionResponse.setDesc("欢迎您，" + inforBean.getName());

        }
        return "redirect:/success";
//        model.addAttribute("response",exceptionResponse);
//        if("1".equals(type)){
//            return "/wxwork/student/qr";
//        }else if("2".equals(type)){
//            return "error";
//        }return "error";
    }

    /**
    * @Description: 企业微信地理位置接口
    * @Param: [lat, lon, poi]
    * @return: java.lang.String
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/wxwork/location/info")
    @ResponseBody
    public String getLocationInfo(Double lat, Double lon, Integer poi) {
        String location = lat + "," + lon;
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(WXWorkConfig.GET_LOCATION_INFO, String.class, location, WXWorkConfig.GAODE_KEY, poi);
        return forObject;
    }
    
//    @GetMapping("/text/card")
//    @ResponseBody
//    public void sendTextCard() {
//        TextCardBean textCardBean = new TextCardBean();
//        TextCardInfoBean textCardInfoBean = new TextCardInfoBean();
//        textCardBean.setAgentid(Integer.parseInt(WXWorkConfig.AGENT_ID));
//        textCardBean.setTouser("@all");
//        textCardInfoBean.setTitle("领奖通知");
//        textCardInfoBean.setDescription("<div class=\"gray\">2016年9月26日</div> <div class=\"normal\">恭喜你抽中iPhone 7一台，领奖码：xxxx</div><div class=\"highlight\">请于2016年10月10日前联系行政同事领取</div>");
//        textCardInfoBean.setUrl(WXWorkConfig.URL + "/student/wxwork/qr");
//        textCardInfoBean.setBtntxt("更多");
//        textCardBean.setTextCard(textCardInfoBean);
//
//        wxWorkService.sendTextCard(textCardBean);
//    }

    /**
    * @Description: 获取企业微信未认证时拦截的请求
    * @Param: [session]
    * @return: com.zut.interactivetools.exception.ExceptionResponse
    * @Author: zjj
    * @Date: 2021/1/5
    */
    @GetMapping("/wxwork/authorize/outUrl")
    @ResponseBody
    public ExceptionResponse goOutUrl(HttpSession session) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        String url = (String) session.getAttribute("outUrl");
        if (url != null && !"".equals(url)) {
            session.removeAttribute("outUrl");
            exceptionResponse.setCode(200);
            exceptionResponse.setMsg(url);
            return exceptionResponse;
        } else {
            exceptionResponse.setCode(500);
            return exceptionResponse;
        }
    }


}
