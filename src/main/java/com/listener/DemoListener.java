package com.listener;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
/**
 * @author xiaoshu
 * 以增量的设计方式去应对这些变化多端的需求
 * -- 模拟充值话费后，发送短信，添加积分
 */

@Component
public class DemoListener {

    @Order(-2)
    @Async("cpuDefaultThreadPool")
    @EventListener
    public void doSomething(SomeEvent event){
        System.out.println("-------监听器1------------");
        System.out.println("监听器一：");
        Long id = event.getId();
        Object param = event.getParam();
        System.out.println("事件id："+id);
        System.out.println("事件参数："+param);
        toDo();
        System.out.println("-------end------------");
    }

    private void toDo(){
        System.out.println("业务处理");
    }

}
