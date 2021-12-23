package com.service.impl;

import com.mapper.UserMapper;
import com.model.User;
import com.service.IAsyncService;
import com.service.IUserService;
import com.tk.TkServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * IUserService
 * @author Hexiaoshu
 * @date 2020-11-28 18:11:57
 */
@Service("userService")
public class UserServiceImpl extends TkServiceImpl<UserMapper,User> implements IUserService {
    @Resource
    IAsyncService asyncService;

    @Resource
    private UserMapper userMapper;
    @Override
    public CompletableFuture<Integer> asyncDemo(User user) {
        System.out.println("异步编辑");
        int i = userMapper.updateByPrimaryKeySelective(user);
        return CompletableFuture.completedFuture(i);
    }

    @Override
    public Object taskDemo() {
        Object o=null;
        System.out.println("开始执行任务");
        System.out.println("3次任务的执行时间一样，并发执行的！");
        CompletableFuture<Object> task1 = asyncService.asyncTask(1);
        CompletableFuture<Object> task2 = asyncService.asyncTask(2);
        CompletableFuture<Object> task3 = asyncService.asyncTask(3);
        try {
            o=CompletableFuture.allOf(task1, task2, task3).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return o;
    }
}
