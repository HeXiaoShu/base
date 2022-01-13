package com.service.impl;

import com.listener.SomeEvent;
import com.service.ISomeService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @Description
 * @Author Hexiaoshu
 * @Date 2022/1/13
 * @modify
 */
@Service("someService")
public class SomeServiceImpl implements ISomeService {
    @Resource
    ApplicationContext applicationContext;

    @Override
    public void toDo(Object source,Long id,Object param) {
        applicationContext.publishEvent(new SomeEvent(source,id,param));
    }
}
