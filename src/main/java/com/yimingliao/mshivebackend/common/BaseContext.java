package com.yimingliao.mshivebackend.common;

/**
 * @author Calendo
 * @version 1.0
 * @description 基于ThreadLocal封装工具类，用户保存和获取当前用户登录id
 * @date 2023/10/16 18:32
 */


public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置线程副本中的值
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取线程副本中的值
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }

}
