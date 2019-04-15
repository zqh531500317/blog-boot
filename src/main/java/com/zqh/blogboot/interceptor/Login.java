package com.zqh.blogboot.interceptor;

import com.zqh.blogboot.controller.WelcomeController;
import com.zqh.blogboot.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Login implements HandlerInterceptor {
    private static final String homePath = "/";
    private static final String adminPath = "admin";
    private static final String apiPath = "api";
    private static final String loginPath = "/manage";
    private static final String resource = "/resource";
    private static final String error = "/error";
    private Logger logger = LoggerFactory.getLogger(Login.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("尝试拦截" + httpServletRequest.getRequestURI());
        if (httpServletRequest.getRequestURI().equals(homePath) ||
                httpServletRequest.getRequestURI().contains(loginPath) ||
                httpServletRequest.getRequestURI().contains(resource) ||
                httpServletRequest.getRequestURI().contains(error) ||
                httpServletRequest.getRequestURI().contains(apiPath)) {
            return true;
        }
        logger.info("拦截特殊资源");
        if (httpServletRequest.getRequestURI().contains(adminPath)) {
            User user = (User) httpServletRequest.getSession().getAttribute("user");
            if (user == null || user.getRole() != 1) {
                logger.info("拦截到admin");
                httpServletResponse.sendRedirect(loginPath);
                return false;
            } else {
                logger.info("通过admin拦截");
                return true;
            }
        }
        logger.info("拦截其他情况");

        //其他情况
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        return user != null && user.getRole() == 1;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
