package com.yimingliao.mshivebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagResult implements Serializable {
    //序列化配置
    private static final long serialVersionUID = 1L;
    //Type
    private String type;
    //Tag
    private String tag;
    //物体坐标位置类
    private BoundingBox boundingBox;

}
