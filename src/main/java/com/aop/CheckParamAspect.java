package com.aop;

import com.common.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import java.util.Objects;

/**
 * @program: he
 * @description: 参数校检
 * @author: he
 * @create: 2020-01-15 10:14
 **/
@Aspect
@Component
public class CheckParamAspect {

    @Pointcut("@annotation(com.annotation.CheckParam)")
    public void checkParam(){}

    @Around(value = "checkParam()")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        BindingResult validResult = (BindingResult)args[args.length-1];
        if (validResult.hasErrors()) {
            return Result.error(Objects.requireNonNull(validResult.getFieldError()).getDefaultMessage());
        }
        return joinPoint.proceed();
    }

}
