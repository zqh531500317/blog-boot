package com.zqh.blogboot.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqh.blogboot.pojo.User;
import com.zqh.blogboot.mapper.UserMapper;
import com.zqh.blogboot.query.UserQuery;
import com.zqh.blogboot.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zqh
 * @since 2019-01-21
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public IPage<User> selectUserList(Page<User> page, UserQuery userQuery) {
        return userMapper.selectUserList(page,userQuery);
    }

    @Override
    public boolean addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public boolean apiAddUser(User user) {
        return userMapper.apiAddUser(user);
    }
}
