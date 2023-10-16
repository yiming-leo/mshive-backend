package com.yimingliao.mshivebackend.entity.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Calendo
 * @version 1.0
 * @description 供物体检测返回坐标数据用
 * @date 2023/10/15 11:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoundingBox implements Serializable {
    //序列化配置
    private static final long serialVersionUID = 1L;
    //高度
    private Integer height;
    //左上角x距离
    private Integer topLeftX;
    //左上角y距离
    private Integer topLeftY;
    //宽度
    private Integer width;
}
