package com.yimingliao.mshivebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Calendo
 * @version 1.0
 * @description TODO
 * @date 2023/10/14 18:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagResult implements Serializable {
    //序列化配置
    private static final long serialVersionUID = 1L;
    //Tag的类型中文
    private String type;
    //Tag的类型英文
    private String typeEng;
    //Tag中文
    private String tag;
    //Tag英文
    private String tagEng;

}
