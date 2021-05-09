package com.zut.interactivetools.config;

import com.zut.interactivetools.bean.UserBean;
import com.zut.interactivetools.bean.UserInforBean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WXWorkInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            UserInforBean user=(UserInforBean) request.getSession().getAttribute("UserInfo");
            if(user!=null){
                return true;
            }
            if(request.getQueryString()!=null&&!"".equals(request.getQueryString())){
                request.getSession().setAttribute("outUrl",request.getRequestURI()+"?"+request.getQueryString());
            }else {
                request.getSession().setAttribute("outUrl",request.getRequestURI());
            }
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<script>");
            out.println("window.open ('/wxwork/index','_top')");
            out.println("</script>");
            out.println("</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
