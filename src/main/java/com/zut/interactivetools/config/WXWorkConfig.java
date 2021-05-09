package com.zut.interactivetools.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationProperties(prefix = "wxconf")
public class WXWorkConfig {

    public static String access_token;
    public static String access_token_stu;
    public static String jsapi_ticket_teacher;
    public static String CORP_ID ;
    public static String CORP_SECRET;
    public static String CORP_SECRET_STU;
    public static String GAODE_KEY;
    public static String AGENT_ID;
    public static String AGENT_ID_STU ;
    public static String GET_ACCESS_TOKEN_URI;
    public static String GET_USER_ID_URI;
    public static String GET_USERINFOR_BY_USERID_URI;
    public static String SEND_MESSAGE_URI ;
    public static String GET_JSAPI_TICKET;
    public static String GET_LOCATION_INFO ;
    //    public static final String URL = "http://3478a54j14.qicp.vip";
    public static String URL ;
    //    private String uploadImagesPath = "D:/upload/images";
    public static String uploadImagesPath ;
    public static String uploadImagesPathMap ;

    public static String getAccess_token() {
        return access_token;
    }

    public static void setAccess_token(String access_token) {
        WXWorkConfig.access_token = access_token;
    }

    public static String getAccess_token_stu() {
        return access_token_stu;
    }

    public static void setAccess_token_stu(String access_token_stu) {
        WXWorkConfig.access_token_stu = access_token_stu;
    }

    public static String getJsapi_ticket_teacher() {
        return jsapi_ticket_teacher;
    }

    public static void setJsapi_ticket_teacher(String jsapi_ticket_teacher) {
        WXWorkConfig.jsapi_ticket_teacher = jsapi_ticket_teacher;
    }

    public static String getCorpId() {
        return CORP_ID;
    }
    @Value("${wxconf.CORP_ID}")
    public void setCorpId(String corpId) {
        CORP_ID = corpId;
    }

    public static String getCorpSecret() {
        return CORP_SECRET;
    }
    @Value("${wxconf.CORP_SECRET}")
    public void setCorpSecret(String corpSecret) {
        CORP_SECRET = corpSecret;
    }

    public static String getCorpSecretStu() {
        return CORP_SECRET_STU;
    }

    @Value("${wxconf.CORP_SECRET_STU}")
    public void setCorpSecretStu(String corpSecretStu) {
        CORP_SECRET_STU = corpSecretStu;
    }

    public static String getGaodeKey() {
        return GAODE_KEY;
    }

    @Value("${wxconf.GAODE_KEY}")
    public void setGaodeKey(String gaodeKey) {
        GAODE_KEY = gaodeKey;
    }

    public static String getAgentId() {
        return AGENT_ID;
    }

    @Value("${wxconf.AGENT_ID}")
    public void setAgentId(String agentId) {
        AGENT_ID = agentId;
    }

    public static String getAgentIdStu() {
        return AGENT_ID_STU;
    }

    @Value("${wxconf.AGENT_ID_STU}")
    public void setAgentIdStu(String agentIdStu) {
        AGENT_ID_STU = agentIdStu;
    }

    public static String getGetAccessTokenUri() {
        return GET_ACCESS_TOKEN_URI;
    }

    @Value("${wxconf.GET_ACCESS_TOKEN_URI}")
    public void setGetAccessTokenUri(String getAccessTokenUri) {
        GET_ACCESS_TOKEN_URI = getAccessTokenUri;
    }

    public static String getGetUserIdUri() {
        return GET_USER_ID_URI;
    }

    @Value("${wxconf.GET_USER_ID_URI}")
    public void setGetUserIdUri(String getUserIdUri) {
        GET_USER_ID_URI = getUserIdUri;
    }

    public static String getGetUserinforByUseridUri() {
        return GET_USERINFOR_BY_USERID_URI;
    }

    @Value("${wxconf.GET_USERINFOR_BY_USERID_URI}")
    public void setGetUserinforByUseridUri(String getUserinforByUseridUri) {
        GET_USERINFOR_BY_USERID_URI = getUserinforByUseridUri;
    }

    public static String getSendMessageUri() {
        return SEND_MESSAGE_URI;
    }

    @Value("${wxconf.SEND_MESSAGE_URI}")
    public void setSendMessageUri(String sendMessageUri) {
        SEND_MESSAGE_URI = sendMessageUri;
    }

    public static String getGetJsapiTicket() {
        return GET_JSAPI_TICKET;
    }

    @Value("${wxconf.GET_JSAPI_TICKET}")
    public void setGetJsapiTicket(String getJsapiTicket) {
        GET_JSAPI_TICKET = getJsapiTicket;
    }

    public static String getGetLocationInfo() {
        return GET_LOCATION_INFO;
    }

    @Value("${wxconf.GET_LOCATION_INFO}")
    public void setGetLocationInfo(String getLocationInfo) {
        GET_LOCATION_INFO = getLocationInfo;
    }

    public static String getURL() {
        return URL;
    }

    @Value("${wxconf.URL}")
    public void setURL(String URL) {
        WXWorkConfig.URL = URL;
    }

    public static String getUploadImagesPath() {
        return uploadImagesPath;
    }

    @Value("${wxconf.uploadImagesPath}")
    public void setUploadImagesPath(String uploadImagesPath) {
        WXWorkConfig.uploadImagesPath = uploadImagesPath;
    }

    public static String getUploadImagesPathMap() {
        return uploadImagesPathMap;
    }

    @Value("${wxconf.uploadImagesPathMap}")
    public void setUploadImagesPathMap(String uploadImagesPathMap) {
        WXWorkConfig.uploadImagesPathMap = uploadImagesPathMap;
    }

    //    private String uploadImagesPathMap = "http://localhost:8080/upload/images";

}