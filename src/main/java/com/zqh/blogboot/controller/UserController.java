package com.zqh.blogboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqh.blogboot.query.Role;
import com.zqh.blogboot.service.EmailService;
import com.zqh.blogboot.utils.MD5Util;
import com.zqh.blogboot.validations.AddUser;
import com.zqh.blogboot.pojo.User;
import com.zqh.blogboot.query.UserQuery;
import com.zqh.blogboot.service.UserService;
import com.zqh.blogboot.utils.AjaxResult;
import com.zqh.blogboot.validations.ApiAddUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zqh
 * @since 2018-12-09
 */
@RestController
@Api("用户操作api")

public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    private static final List<Role> roles = new ArrayList<Role>() {{
        add(new Role().setId(1).setName("系统管理员"));
        add(new Role().setId(2).setName("普通用户"));
    }};

    @ApiOperation(value = "获取用户所有的角色")
    @GetMapping("/admin/user/roles")
    public List<Role> roles() {
        return roles;
    }

    @ApiOperation(value = "获取用户列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "role", value = "角色"),
                    @ApiImplicitParam(name = "username", value = "用户名"),
                    @ApiImplicitParam(name = "datemin", value = "最小创建时间"),
                    @ApiImplicitParam(name = "datemin", value = "最大创建时间"),
                    @ApiImplicitParam(name = "locked", value = "是否被锁定")
            }
    )
    @GetMapping("/admin/user/list")
    public IPage<User> selectUserList(@RequestParam(defaultValue = "1", required = false) Integer current,
                                      @RequestParam(defaultValue = "10", required = false) Integer pageSize, User user,
                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date datemax,
                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date datemin) {
        UserQuery userQuery = new UserQuery();
        userQuery.setRole(user.getRole())
                .setUsername(user.getUsername())
                .setDatemax(datemax)
                .setDatemin(datemin)
                .setLocked(user.getLocked());
        Page<User> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(current);
        return userService.selectUserList(page, userQuery);
    }

    @ApiOperation(value = "管理员添加用户")
    @PostMapping("/admin/user/")
    public AjaxResult addUser(@RequestBody @Validated({AddUser.class}) User user, BindingResult bindingResult) {
        AjaxResult ajaxResult = new AjaxResult();
        if (bindingResult.getAllErrors().size() != 0) {
            ajaxResult.setResult(false);
            ajaxResult.setData("添加失败,格式不正确");
        }
        boolean b = userService.addUser(user);
        if (b) {
            ajaxResult.setResult(true);
            ajaxResult.setData("添加成功");
        } else {
            ajaxResult.setResult(false);
            ajaxResult.setData("添加失败");
        }
        return ajaxResult;
    }

    @ApiOperation(value = "注册用户")
    @PostMapping("/api/user/")
    public AjaxResult apiAddUser(@RequestBody @Validated({ApiAddUser.class}) User user, BindingResult bindingResult, HttpSession httpSession) {
        AjaxResult ajaxResult = new AjaxResult();
        if (bindingResult.getAllErrors().size() != 0) {
            ajaxResult.setResult(false);
            ajaxResult.setData("添加失败,格式不正确");
        }
        boolean b = userService.apiAddUser(user);
        if (b) {
            ajaxResult.setResult(true);
            ajaxResult.setData("添加成功");
            String checkEmail = UUID.randomUUID().toString().substring(0, 6);
            httpSession.setAttribute("id", user.getUserId());
            httpSession.setAttribute("checkEmail", checkEmail);
            //后期可改为异步邮件
            emailService.sendSimpleEmail(user.getEmail(), "博客验证邮箱", "验证码为" + checkEmail);
        } else {
            ajaxResult.setResult(false);
            ajaxResult.setData("添加失败");
        }
        return ajaxResult;
    }

    @ApiOperation(value = "验证邮箱")
    @PostMapping("/api/user/checkEmail/{email}")
    public boolean checkEmail(HttpSession httpSession, @PathVariable("email") String email) {
        String checkEmail = (String) httpSession.getAttribute("checkEmail");
        if (checkEmail == null)
            return false;
        if (!checkEmail.equals(email)) {
            return false;
        }
        User user = new User();
        user.setUserId((Integer) httpSession.getAttribute("id"));
        user.setLocked(false);
        userService.updateById(user);
        return true;
    }

    @ApiOperation(value = "找回密码")
    @PostMapping("/api/user/forget/{status}")
    public boolean forget(@PathVariable(value = "status") Integer status, HttpSession httpSession, String username, String check, String password) throws Exception {
        //填写账号
        if (status == 0) {
            User user = userService.getOne(new QueryWrapper<User>()
                    .eq("username", username)
                    .eq("is_delete", false)
                    .eq("locked", false));
            if (user == null)
                return false;
            httpSession.setAttribute("forget-user", user);
            String email = user.getEmail();
            String key = UUID.randomUUID().toString().substring(0, 10);
            emailService.sendSimpleEmail(email, "找回密码", key);
            httpSession.setAttribute("forget-key", key);
            return true;
        }
        //身份验证
        else if (status == 1) {
            User user = (User) httpSession.getAttribute("forget-user");
            String key = (String) httpSession.getAttribute("forget-key");
            if (user == null || key == null || check == null)
                return false;
            if (!check.equals(key))
                return false;
            httpSession.setAttribute("checkStatus", true);
            return true;
        }
        //设置新密码
        else if (status == 2) {
            User user = (User) httpSession.getAttribute("forget-user");
            String key = (String) httpSession.getAttribute("forget-key");
            Boolean b = (Boolean) httpSession.getAttribute("checkStatus");
            if (user == null || user.getUserId() == null || key == null || b == null || password == null || password.equals(""))
                return false;
            User newUser = new User().setUserId(user.getUserId())
                    .setPassword(MD5Util.EncoderByMd5(password));
            userService.updateById(newUser);
            httpSession.invalidate();
            return true;
        }
        return true;
    }

    @ApiOperation(value = "锁定用户")
    @PutMapping("/admin/user/lock/{id}")
    public void lock(@PathVariable Integer id) {
        User user = new User();
        user.setUserId(id);
        user.setLocked(true);
        userService.updateById(user);
    }

    @ApiOperation(value = "解锁用户")
    @PutMapping("/admin/user/unlock/{id}")
    public void unlock(@PathVariable Integer id) {
        User user = new User();
        user.setUserId(id);
        user.setLocked(false);
        userService.updateById(user);
    }

    @ApiOperation(value = "更新用户信息")
    @PutMapping("/admin/user/")
    public AjaxResult update(@Validated({AddUser.class}) User user) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setResult(userService.updateById(user));
        ajaxResult.autoData();
        return ajaxResult;
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/admin/user/{id}")
    public void deleteById(@PathVariable Integer id) {
        userService.removeById(id);
    }

    @ApiOperation(value = "批量删除用户")
    @DeleteMapping("/admin/user")
    public boolean deleteByIds(Integer[] id) {
        return userService.removeByIds(Arrays.asList(id));
    }
}