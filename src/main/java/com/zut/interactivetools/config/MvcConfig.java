package com.zut.interactivetools.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MvcConfig {
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/index").setViewName("index");
                registry.addViewController("/my").setViewName("my");
                registry.addViewController("/login").setViewName("login");
                registry.addViewController("/courseList").setViewName("courseList");
                registry.addViewController("/questionList").setViewName("questionList");
                registry.addViewController("/course").setViewName("course");
                registry.addViewController("/course/sign/reserve").setViewName("inCourse/signyuding");
                registry.addViewController("/course/sign").setViewName("inCourse/signin");
                registry.addViewController("/course/sign/census").setViewName("inCourse/census");

                registry.addViewController("/course/class").setViewName("inCourse/class");
                registry.addViewController("/course/class/detail").setViewName("inCourse/classDetail");
                registry.addViewController("/course/set").setViewName("inCourse/set");
                registry.addViewController("/course/timer").setViewName("inCourse/timer");
                registry.addViewController("/course/people").setViewName("inCourse/people");
                registry.addViewController("/course/people/detail").setViewName("inCourse/peopleDetail");
                registry.addViewController("/course/ask").setViewName("inCourse/ask");
                registry.addViewController("/course/lottery").setViewName("inCourse/lottery");
                registry.addViewController("/course/talk").setViewName("inCourse/talk");
                registry.addViewController("/course/talk/content").setViewName("inCourse/talkContent");
                registry.addViewController("/course/talk/detail").setViewName("inCourse/talkDetail");
//                registry.addViewController("/course/set").setViewName("inCourse/set");
                registry.addViewController("/course/sign/signDetail").setViewName("inCourse/signinDetail");
                registry.addViewController("/wxwork/teacher/index").setViewName("WXWork/mobile_index");
                registry.addViewController("/wxwork/teacher/choice").setViewName("WXWork/choice2");

                registry.addViewController("/wxwork/teacher/location").setViewName("WXWork/locationTeacher");
                registry.addViewController("/wxwork/student/location").setViewName("WXWork/locationStudent");
                registry.addViewController("/wxwork/student/qr").setViewName("WXWork/qr");
                registry.addViewController("/wxwork/student/signResult").setViewName("WXWork/signResult");
                registry.addViewController("/wxwork/student/stuTalk").setViewName("WXWork/stuTalk");
                registry.addViewController("/wxwork/student/stuTest").setViewName("WXWork/stutest");
                registry.addViewController("/wxwork/student/sign/phone").setViewName("WXWork/phoneLogin");
                registry.addViewController("/question").setViewName("question");
                registry.addViewController("/question/detail").setViewName("questionDetail");
                registry.addViewController("/course/test").setViewName("inCourse/test");
                registry.addViewController("/course/test/reserve").setViewName("inCourse/testyuding");
                registry.addViewController("/course/test/content").setViewName("inCourse/testContent");
                registry.addViewController("/course/test/history").setViewName("inCourse/history_test");
                registry.addViewController("/course/test/history/detail").setViewName("inCourse/history_questionDetail");
                registry.addViewController("/course/test/history/detail/analyse").setViewName("inCourse/testStudentAnalyse");
                registry.addViewController("/course/test/teacher/content").setViewName("inCourse/testTeacherContent");
                registry.addViewController("/teacher/signCode").setViewName("inCourse/signinCode");
                registry.addViewController("/wxwork/index").setViewName("wxWorkIndex");
                registry.addViewController("/error").setViewName("error");
                registry.addViewController("/success").setViewName("success");
                registry.addViewController("/course/lottery/content").setViewName("inCourse/lotteryContent");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                InterceptorRegistration interceptorRegistration = registry.addInterceptor(new AdminInterceptor());
                interceptorRegistration.addPathPatterns("/**");
                interceptorRegistration.excludePathPatterns(
                        "/", "/teacher/login", "/login", "/static/**", "/wxwork/**", "/success", "/favicon.ico", "/error","/upload/images/**"
                );
                InterceptorRegistration interceptorRegistration1 = registry.addInterceptor(new WXWorkInterceptor());
                interceptorRegistration1.addPathPatterns("/wxwork/**");
                interceptorRegistration1.excludePathPatterns(
                        "/wxwork/index", "/wxwork/authorize/wxconfig", "/wxwork/authorize/getAuthorizePath", "/wxwork/authorize/getCode",
                        "/static/**", "/error", "/favicon.ico", "/success"
                );
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//                registry.addResourceHandler("/upload/images/**").addResourceLocations("file:D://upload/images/");
                registry.addResourceHandler("/upload/images/**").addResourceLocations("file:"+WXWorkConfig.uploadImagesPath+"/");
            }
        };

    }
}
