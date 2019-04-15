package com.zqh.blogboot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqh.blogboot.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zqh.blogboot.query.UserQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zqh
 * @since 2019-01-21
 */
public interface UserService extends IService<User> {
    IPage<User> selectUserList(Page<User> page, UserQuery userQuery);

    boolean addUser(User user);

    boolean apiAddUser(User user);

}
