package com.yimingliao.mshivebackend.entity.mongodb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yimingliao.mshivebackend.config.AutoIncKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author Calendo
 * @version 1.0
 * @description MongoDB Room Entity
 * @date 2023/10/17 21:24
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Document(collection = "room")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    //mongodb自动生成的主键，与_id对应
    //此ID即为UUID，供ref查找、删除、管理员端查找使用
    @Id
    private String id;

    //此ID为增量ID，为分页查询使用
    @Indexed
    @AutoIncKey
    @Field("room_id")
    private Long roomId = 0L;

    //房间名称
    @Field("name")
    @JsonProperty("name")
    private String name;

    //房间属性
    @Field("attribute")
    @JsonProperty("attribute")
    private String attribute;

    //主颜色
    @Field("main_color")
    @JsonProperty("main_color")
    private String mainColor;

    //副颜色
    @Field("minor_color")
    @JsonProperty("minor_color")
    private String minorColor;

    //图片地址
    @Field("img_url")
    @JsonProperty("img_url")
    private String imgUrl;

    //描述
    @Field("description")
    @JsonProperty("description")
    private String description;

    //标记
    @Field("is_bookmarks")
    @JsonProperty("is_bookmarks")
    private Boolean isBookmarks;

    //修改次数
    @Field("modify_count")
    @JsonProperty("modify_count")
    private Integer modifyCount;

    //上次修改时间
    @Field("modify_time")
    @JsonProperty("modify_time")
    private String modifyTime;

    //是否已删除
    @Field("is_deleted")
    @JsonProperty("is_deleted")
    private Boolean isDeleted;

    //用户归属指向
    @Field("ref_user_id")
    @JsonProperty("ref_user_id")
    private String refUserId;
}
