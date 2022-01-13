package com.listener;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * @author xiaoshu
 * 场景：联动需求，频繁变化需求！
 *      - 以增量的设计方式去应对这些变化多端的需求
 * 事件 < - 监听
 * 默认： 一个监听出现异常，就会停止执行，同步操作。 @Async 提供异步
 *  谁数值小就执行顺序排越前面 @Order(-1)
 */
@Setter
@Getter
@ToString
public class SomeEvent extends ApplicationEvent {

    private static final long serialVersionUID = 2977805511138037179L;
    /**
     * 业务id
     */
    private Long id;

    /**
     * 业务对象
     */
    private Object param;

    public SomeEvent(Object source,Long id,Object param) {
        super(source);
        this.id=id;
        this.param=param;
    }

}
