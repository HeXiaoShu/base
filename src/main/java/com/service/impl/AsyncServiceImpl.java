package com.service.impl;

import com.service.IAsyncService;
import com.util.DateUtil;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

/**
 * @Description 异步线程业务接口
 * @Author Hexiaoshu
 * @Date 2021/12/23
 * @modify
 */
@Service("asyncService")
public class AsyncServiceImpl implements IAsyncService {

    @Override
    public CompletableFuture<Object> asyncTask(Integer param) {
        System.out.println(System.currentTimeMillis());
        System.out.println("开始请求类型"+param+"的数据");
        return CompletableFuture.completedFuture(param+1);
    }
}
