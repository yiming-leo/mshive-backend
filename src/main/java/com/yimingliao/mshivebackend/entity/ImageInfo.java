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
public class ImageInfo implements Serializable{
    //序列化配置
    private static final long serialVersionUID = 1L;
    //图片的公网url，需要图床或CDN
    private String imgUrl;
    //是否需要返回坐标：true或false，同时也是2种不同AI接口的区分属性
    private String needDet;
    //i18n设置：zh或en，默认全部
    private String language;
    //置信度设置，越大越苛刻，建议：1.物品检测：20, 2.家具检测：30
    private String threshold;
    //物体检测数量限制，建议：1.物品检测：15个, 2.家具检测：5个
    private Integer limit;
    //AI厂商名：huawei, tencent, alibaba, deepl, google, azure, aws
    private String serverName;
    //SecretKey
    private SecretKey secretKey;
}
