package com.electricity.common.annotation;

import java.lang.annotation.*;


/**
 * @Description: 自定义权限注解
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresPermissions {

    String[] permissions();
}
