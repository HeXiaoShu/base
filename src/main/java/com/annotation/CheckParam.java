package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: he
 * @description: 控制器参数检查, 配合springframework.validation 参数校检
 *               BindingResult 放在参数最后
 * @author: he
 * @create: 2020-01-15 10:15
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD })
public @interface CheckParam {


}
