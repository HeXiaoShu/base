package com.service;

import com.model.User;
import com.tk.TkService;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

/**
 * IUserService
 * @author Hexiaoshu
 * @date 2020-11-28 18:11:57
 */
public interface IUserService extends TkService<User> {

    /**
     * 异步编辑用户 demo
     * @param user
     * @return
     */
    @Async("ioDefaultThreadPool")
    CompletableFuture<Integer> asyncDemo(User user);

    /**
     * Demo 任务需要执行多次请求，然后封装数据
     * @return
     */
    Object taskDemo();

}
