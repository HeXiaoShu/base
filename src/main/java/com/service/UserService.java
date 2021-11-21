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
public interface UserService extends TkService<User> {

    /**
     * 异步编辑用户 demo
     * @param user
     * @return
     */
    @Async("ioDefaultThreadPool")
    CompletableFuture<Integer> asyncDemo(User user);

}
