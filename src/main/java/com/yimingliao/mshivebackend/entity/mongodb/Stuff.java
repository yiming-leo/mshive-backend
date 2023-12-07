package com.yimingliao.mshivebackend.entity.mongodb;

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
 * @description MongoDB Stuff Entity
 * @date 2023/10/28 18:41
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "stuff")
public class Stuff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    //此ID为增量ID，为分页查询使用
    @Indexed
    @AutoIncKey
    @Field("stuff_id")
    private Long stuffId = 0L;

    //家具名称
    @Field("name")
    //@JsonProperty("name")
    private String name;

    //家具属性
    @Field("attribute")
    //@JsonProperty("attribute")
    private String attribute;

    //主颜色
    @Field("main_color")
    //@JsonProperty("mainColor")
    private String mainColor;

    //副颜色
    @Field("minor_color")
    //@JsonProperty("minorColor")
    private String minorColor;

    //图片地址
    @Field("img_url")
    //@JsonProperty("imgUrl")
    private String imgUrl;

    //描述
    @Field("description")
    //@JsonProperty("description")
    private String description;

    //标记
    @Field("is_bookmarks")
    //@JsonProperty("isBookmarks")
    private Boolean isBookmarks;

    //修改次数
    @Field("modify_count")
    //@JsonProperty("modifyCount")
    private Integer modifyCount;

    //上次修改时间
    @Field("modify_time")
    //@JsonProperty("modifyTime")
    private String modifyTime;

    //是否已删除
    @Field("is_deleted")
    //@JsonProperty("isDeleted")
    private Boolean isDeleted;

    //用户归属指向
    @Field("ref_user_id")
    //@JsonProperty("refUserId")
    private String refUserId;

    //房间归属指向
    @Field("ref_room_id")
    //@JsonProperty("refRoomId")
    private String refRoomId;

    //物品归属指向
    @Field("ref_furniture_id")
    //@JsonProperty("refFurnitureId")
    private String refFurnitureId;
}