package com.zut.interactivetools.wxwork;


import com.zut.interactivetools.bean.AccessTokenBean;
import com.zut.interactivetools.bean.JsapiTicketBean;
import com.zut.interactivetools.config.WXWorkConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AccessTokenTask {

    @Scheduled(fixedRate = 2400*1000)
    private void getAccessTokenTask(){

        RestTemplate restTemplate = new RestTemplate();
        AccessTokenBean object = restTemplate.getForObject(WXWorkConfig.GET_ACCESS_TOKEN_URI,
                AccessTokenBean.class, WXWorkConfig.CORP_ID, WXWorkConfig.CORP_SECRET);
        if(object.getErrcode()==0){
            WXWorkConfig.access_token = object.getAccess_token();
//            System.out.println(WXWorkConfig.access_token);
            JsapiTicketBean forObject = restTemplate.getForObject(WXWorkConfig.GET_JSAPI_TICKET, JsapiTicketBean.class, WXWorkConfig.access_token);
            WXWorkConfig.jsapi_ticket_teacher = forObject.getTicket();
//            System.out.println(WXWorkConfig.jsapi_ticket_teacher);
        }else {

        }
    }
    @Scheduled(fixedRate = 2400*1000)
    private void getStuAccessTokenTask(){

        RestTemplate restTemplate = new RestTemplate();
        AccessTokenBean object = restTemplate.getForObject(WXWorkConfig.GET_ACCESS_TOKEN_URI,
                AccessTokenBean.class, WXWorkConfig.CORP_ID, WXWorkConfig.CORP_SECRET_STU);
        if(object.getErrcode()==0){
            WXWorkConfig.access_token_stu = object.getAccess_token();
//            System.out.println(WXWorkConfig.access_token_stu);
        }else {

        }
    }

}
