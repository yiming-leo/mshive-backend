package com.yimingliao.mshivebackend.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Calendo
 * @version 1.0
 * @description 定义MongoDB的自动增长注解
 * @date 2023/10/18 13:23
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoIncKey {
}
