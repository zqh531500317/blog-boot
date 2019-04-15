package com.zqh.blogboot.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqh.blogboot.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zqh.blogboot.query.UserQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zqh
 * @since 2019-01-21
 */
public interface UserMapper extends BaseMapper<User> {
    boolean addUser(User user);
    IPage<User> selectUserList(Page page,@Param("userQuery") UserQuery userQuery);
    void incArticleNum(Integer userId);
    void incCommentNum(Integer userId);
    boolean apiAddUser(User user);
}
