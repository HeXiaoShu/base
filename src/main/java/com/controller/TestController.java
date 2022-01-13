package com.controller;

import com.common.Result;
import com.constant.DateOrder;
import com.model.User;
import com.service.IUserService;
import com.util.SnowflakeIdWorker;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    IUserService userService;

    @GetMapping
    public Result test(){
        return Result.ok(userService.getAll());
    }

    @GetMapping("/page")
    public Result page(){
        User user = new User().setStatus("1");
        return Result.ok(userService.getListEqualPage(user, DateOrder.DESC,1,2));
    }

    @PostMapping
    public Result addBatch(@RequestBody List<User> users){
        users.forEach(e->e.setId(SnowflakeIdWorker.id()));
        Integer count = userService.insertBatch(users);
        return Result.ok("添加成功,数据："+count);
    }




}
