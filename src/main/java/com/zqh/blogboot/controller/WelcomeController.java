package com.zqh.blogboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zqh.blogboot.pojo.User;
import com.zqh.blogboot.service.UserService;
import com.zqh.blogboot.utils.MD5Util;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class WelcomeController {
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(WelcomeController.class);

    @ApiOperation(value = "首页")
    @GetMapping("/")
    public String welcome() {
        return "blog/index";
    }

    @ApiOperation(value = "管理员登录")
    @ResponseBody
    @PostMapping("/manage")
    public boolean managePage(HttpSession httpSession, String username, String password) throws Exception {
        User user = userService.getOne(new QueryWrapper<User>()
                .eq("username", username)
                .eq("password", MD5Util.EncoderByMd5(password))
                .eq("locked", false)
                .eq("role", "1")
        );
        if (user == null)
            return false;
        httpSession.setAttribute("user", user);
        logger.info(user.getUsername() + "权限登录");
        return true;
    }

    @ApiOperation(value = "管理主页")
    @GetMapping("/admin/manage")
    public String manage() {
        return "blogManager/index";
    }

    @ApiOperation(value = "登录")
    @ResponseBody
    @PostMapping("/api/login")
    public boolean login(HttpSession httpSession, String username, String password) throws Exception {
        User user = userService.getOne(new QueryWrapper<User>()
                .eq("username", username)
                .eq("password", MD5Util.EncoderByMd5(password))
                .eq("locked", false)
        );
        if (user == null)
            return false;
        httpSession.setAttribute("user", user);
        logger.info(user.getUsername() + "普通登录");
        return true;
    }

    @ApiOperation(value = "登出")
    @ResponseBody
    @PostMapping("/api/logout")
    public boolean logout(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null)
            return true;
        httpSession.invalidate();
        logger.info(user.getUsername() + "退出登录");
        return true;
    }
}
