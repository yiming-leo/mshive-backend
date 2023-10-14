package com.yimingliao.mshivebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Calendo
 * @version 1.0
 * @description TODO
 * @date 2023/10/14 20:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageInfo implements Serializable {
    //序列化配置
    private static final long serialVersionUID = 1L;
    //图片的url
    private String imgUrl;
}
