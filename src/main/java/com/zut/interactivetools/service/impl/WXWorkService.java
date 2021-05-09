package com.zut.interactivetools.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zut.interactivetools.bean.TextCardBean;
import com.zut.interactivetools.bean.UserIdBean;
import com.zut.interactivetools.bean.UserInforBean;
import com.zut.interactivetools.config.WXWorkConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Service
public class WXWorkService {
    public void sendTextCard(TextCardBean textCard){
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String s = JSON.toJSONString(textCard);
        JSONObject result = restTemplate.postForObject(WXWorkConfig.SEND_MESSAGE_URI, s, JSONObject.class,WXWorkConfig.access_token);
        System.out.println(result.toString());
    }

    public void sendStuTextCard(TextCardBean textCard){
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String s = JSON.toJSONString(textCard);
        JSONObject result = restTemplate.postForObject(WXWorkConfig.SEND_MESSAGE_URI, s, JSONObject.class,WXWorkConfig.access_token_stu);
    }

    public String getUserId(String code) {
        RestTemplate restTemplate = new RestTemplate();
        String res = null;
        String msg = restTemplate.getForObject(WXWorkConfig.GET_USER_ID_URI, String.class, WXWorkConfig.access_token, code);
        UserIdBean parse = JSON.parseObject(msg, UserIdBean.class);
        if(parse.getErrcode()==0){
            res = parse.getUserId();
        }
        return res;
    }


    public UserInforBean getUserInforByUserId(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        UserInforBean msg = restTemplate.getForObject(WXWorkConfig.GET_USERINFOR_BY_USERID_URI,
                UserInforBean.class, WXWorkConfig.access_token, userId);

        return msg;
    }
}
