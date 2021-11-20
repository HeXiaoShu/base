package com.controller;

import com.common.Result;
import com.constant.DateOrder;
import com.model.User;
import com.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.*;

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
    private UserService userService;

    @GetMapping
    public Result get(){
        return Result.ok(1);
    }






}
