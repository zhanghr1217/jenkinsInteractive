package com.zut.interactivetools;

import com.zut.interactivetools.config.WXWorkConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication

@EnableScheduling

@MapperScan("com.zut.interactivetools.dao")

//@EnableConfigurationProperties({ WXWorkConfig.class })

@EnableTransactionManagement
public class InteractivetoolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(InteractivetoolsApplication.class, args);
    }

}
