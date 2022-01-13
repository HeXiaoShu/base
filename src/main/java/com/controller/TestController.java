package com.controller;

import com.common.Result;
import com.model.User;
import com.service.ISomeService;
import com.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Description
 * @Author Hexiaoshu
 * @Date 2021/4/17
 * @modify
 */
@Api(value = "测试")
@RequestMapping("/test")
@RestController
public class TestController {

    @Resource
    private IUserService userService;
    @Resource
    private ISomeService someService;

    @GetMapping
    public Result get() throws ExecutionException, InterruptedException {
        System.out.println("开始...");
        CompletableFuture<Integer> demo = userService.asyncDemo(new User().setId(1L).setUserName("小树1"));
        //demo.get(); 取值阻塞
        System.out.println("结束...");
        return Result.ok(1);
    }

    @GetMapping("/task")
    public Result task(){
        Object o = userService.taskDemo();
        someService.toDo(new User().setUserName("小树").setId(1L),1L,new User().setUserName("小树").setId(1L));
        return Result.ok(o);
    }







}
