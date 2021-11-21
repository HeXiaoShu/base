package com.service.impl;

import com.mapper.UserMapper;
import com.model.User;
import com.service.UserService;
import com.tk.TkServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * IUserService
 * @author Hexiaoshu
 * @date 2020-11-28 18:11:57
 */
@Service("userService")
public class UserServiceImpl extends TkServiceImpl<UserMapper,User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public CompletableFuture<Integer> asyncDemo(User user) {
        System.out.println("异步编辑");
        int i = userMapper.updateByPrimaryKeySelective(user);
        return CompletableFuture.completedFuture(i);
    }
}
