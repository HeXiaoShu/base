package com.util;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description 多线程工具类
 * @Author Hexiaoshu
 * @Date 2021/11/22
 * @modify
 */
public class ThreadUtil {

    private static final int core= Runtime.getRuntime().availableProcessors();
    private static final int waitCount = 20;

    private static final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor
                    (core,
                    core + waitCount,
                    60*10, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(waitCount),
                    new CustomizableThreadFactory("toolPool-"),
                    new ThreadPoolExecutor.CallerRunsPolicy());


}
