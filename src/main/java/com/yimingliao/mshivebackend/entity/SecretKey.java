package com.yimingliao.mshivebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Calendo
 * @version 1.0
 * @description 密钥类，是一个存储第三方API的AK和SK的虚拟类
 * @date 2023/10/16 16:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecretKey implements Serializable {
    //序列化配置
    private static final long serialVersionUID = 1L;
    //AK/SI
    private String ak;
    //SK
    private String sk;
}
