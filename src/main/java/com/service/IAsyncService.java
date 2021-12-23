package com.service;

import org.springframework.scheduling.annotation.Async;
import java.util.concurrent.CompletableFuture;

/**
 * @Description 异步线程业务接口,下发接口都是异步子任务
 * @Author Hexiaoshu
 * @Date 2021/12/23
 * @modify
 */
public interface IAsyncService {

    @Async("cpuDefaultThreadPool")
    CompletableFuture<Object> asyncTask(Integer param);

}
