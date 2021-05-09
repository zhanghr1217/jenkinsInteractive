package com.zut.interactivetools.config;

import com.zut.interactivetools.bean.SubmitTaskBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @program: interactivetools_integration
 * @description: redis 配置
 * @author: zjj
 * @create: 2021-02-05 17:59
 **/

@Configuration
public class RedisConfig {


    @Bean
    public RedisTemplate<Object, SubmitTaskBean> taskBeanRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, SubmitTaskBean> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(SubmitTaskBean.class);
        template.setDefaultSerializer(serializer);
        return template;
    }


}
