package com.zut.interactivetools.service.impl;

import com.zut.interactivetools.bean.SubmitTaskBean;
import com.zut.interactivetools.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @program: interactivetools_integration
 * @description: redis
 * @author: zjj
 * @create: 2021-02-05 17:50
 **/

@Service
public class RedisService {

    @Autowired
    RedisTemplate taskBeanRedisTemplate;


    public void putTask(SubmitTaskBean submitTaskBean){
//        taskBeanRedisTemplate.opsForHash().put("Tool:ToolTask",submitTaskBean.getTimestamp(),submitTaskBean);
        Boolean b = taskBeanRedisTemplate.opsForZSet().add("Tool:ToolTaskSet", submitTaskBean, submitTaskBean.getTaskTimestamp());
        if (!b){
            throw new MyException(501,"putTask","添加失败");
        }
    }

    public List<SubmitTaskBean> getTask(long now){
        Set range = taskBeanRedisTemplate.opsForZSet().rangeByScore("Tool:ToolTaskSet", now , now+30 * 1000);
        ArrayList<SubmitTaskBean> submitTaskBeans = new ArrayList<>();
        Iterator<SubmitTaskBean> iterator = range.iterator();
        while (iterator.hasNext()){
            SubmitTaskBean next = iterator.next();
            submitTaskBeans.add(next);
        }
        return submitTaskBeans;
    }

    public void removeTask(long now){
        Long remove = taskBeanRedisTemplate.opsForZSet().removeRangeByScore("Tool:ToolTaskSet", now, now+30 * 1000);
    }

    public void removeTask(SubmitTaskBean submitTask){
        Long remove = taskBeanRedisTemplate.opsForZSet().remove("Tool:ToolTaskSet", submitTask);
    }
}
