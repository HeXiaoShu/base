package com.controller;

import com.common.Result;
import com.github.pagehelper.PageInfo;
import com.model.City;
import com.service.CityService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

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
    private CityService cityService;

    @GetMapping
    public Result get(){
        return Result.ok(null);
    }






}
