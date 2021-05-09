package com.zut.interactivetools.controller;

import com.zut.interactivetools.bean.TermBean;
import com.zut.interactivetools.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: interactivetools
 * @description: admin
 * @author: zjj
 * @create: 2021-04-26 17:59
 **/

@Controller
public class AdminController {

    @Autowired
    TermService termService;


}
