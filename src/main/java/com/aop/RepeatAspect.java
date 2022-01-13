package com.aop;

import com.common.Result;
import com.github.benmanes.caffeine.cache.Cache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @program: he
 * @description: 参数校检
 * @author: he
 * @create: 2020-01-15 10:14
 **/
@Aspect
@Component
public class RepeatAspect {

    @Resource
    Cache<String, Object> caffeineCache;

    @Pointcut("@annotation(com.annotation.Repeat)")
    public void checkParam(){}

    @Around(value = "checkParam()")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        if (checkRepeat()){
            return Result.error("重复提交");
        }
        return joinPoint.proceed();
    }

    private boolean checkRepeat(){
        Object repeat = caffeineCache.getIfPresent("repeat");
        if (repeat==null){
            caffeineCache.put("repeat",1);
        }else {
            return true;
        }
        return false;
    }

}
