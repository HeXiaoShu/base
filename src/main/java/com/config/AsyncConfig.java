package com.config;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * – new ThreadPoolExecutor.AbortPolicy() 不处理并且抛出异常
 * – new ThreadPoolExecutor.CallerRunsPolicy() 退回发出线程的地方
 * – new ThreadPoolExecutor.DiscardPolicy() 抛弃，不抛出异常
 * – new ThreadPoolExecutor.DiscardOldestPolicy() 尝试与最早的线程竞争，不抛出异常
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * 服务核数
     */
    private static int core = Runtime.getRuntime().availableProcessors();


    /**
     * CPU 密集型，比如复杂运算: 加密、解密、压缩、计算
     * 高并发、任务执行时间短的业务，线程池线程数可以设置为CPU核数+1，减少线程上下文的切换
     * 默认策略 CallerRunsPolicy(), 让发出线程继续执行任务，不考虑任务的顺序问题。
     * @return Executor
     */
    @Bean("cpuDefaultThreadPool")
    public Executor cpuDefaultThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(core+1);
        executor.setMaxPoolSize(core+21);
        executor.setQueueCapacity(20);
        executor.setThreadNamePrefix("cpuPool-");
        executor.setKeepAliveSeconds(60);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * 耗时 IO 型，比如数据库、文件的读写，网络通信等任务，这种任务的特点是并不会特别消耗 CPU 资源，但是 IO 操作很耗时
     * 默认策略 CallerRunsPolicy(), 让发出线程继续执行任务，不考虑任务的顺序问题。
     * @return Executor
     */
    @Bean("ioDefaultThreadPool")
    public Executor ioDefaultThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //TODO 阻塞系数,越高核心线程数越高
        double blockingCoefficient = 0.9;
        int poolSize = (int) (core / (1 - blockingCoefficient));
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(poolSize+50);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("cpuPool-");
        executor.setKeepAliveSeconds(120);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }





}
