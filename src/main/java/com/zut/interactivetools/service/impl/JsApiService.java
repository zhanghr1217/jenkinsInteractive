package com.zut.interactivetools.service.impl;

import com.zut.interactivetools.config.WXWorkConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class JsApiService {
    public String JsSdkSignature(String noncestr,long timestamp,String url){
        String s = "jsapi_ticket="+ WXWorkConfig.jsapi_ticket_teacher+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
        String signature = DigestUtils.sha1Hex(s);
        return signature;
    }
}
