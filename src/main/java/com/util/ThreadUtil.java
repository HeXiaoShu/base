package com.util;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description 线程工具类
 * 1. CPU 密集型任务，比如加密、解密、压缩、计算
 *     -   CPU 核心数的 1~2 倍
 * 2. 耗时 IO 型，比如数据库、文件的读写，网络通信等任务，这种任务的特点是并不会特别消耗 CPU 资源，但是 IO 操作很耗时
 *     -  线程数 = CPU 核心数 *(1+平均等待时间/平均工作时间)
 * – new ThreadPoolExecutor.AbortPolicy() 不处理并且抛出异常
 * – new ThreadPoolExecutor.CallerRunsPolicy() 退回发出线程的地方
 * – new ThreadPoolExecutor.DiscardPolicy() 抛弃，不抛出异常
 * – new ThreadPoolExecutor.DiscardOldestPolicy() 尝试与最早的线程竞争，不抛出异常

 * @Author Hexiaoshu
 * @Date 2021/8/10
 * @modify
 */
public class ThreadUtil {

     /**
     * Cpu型默认线程池
     * @param wait     等带线程数
     * @param poolName 线程池名称前缀
     * @param handler  拒绝策略
     */
    public ThreadPoolExecutor getCpuDefaultThreadPool(Integer wait, String poolName, RejectedExecutionHandler handler){
        int core = Runtime.getRuntime().availableProcessors();
        int max = core + wait + 1;
        return new ThreadPoolExecutor(core,max, 30L,TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(wait),
                new CustomizableThreadFactory(String.format("%s-", poolName)),
                handler);
    }


    public ThreadPoolExecutor getIoDefaultThreadPool(Integer avgWaitTime,Integer avgTaskTime,Integer wait ,String poolName,RejectedExecutionHandler handler){
        int cpuCount = Runtime.getRuntime().availableProcessors();
        int core = cpuCount * (1+avgWaitTime/avgTaskTime);
        int max = core + wait;
        return new ThreadPoolExecutor(core,max, avgWaitTime, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(wait),
                new CustomizableThreadFactory(String.format("%s-", poolName)),
                handler);
    }


}
