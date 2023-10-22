package com.yimingliao.mshivebackend.entity.elastic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author Calendo
 * @version 1.0
 * @description Elasticsearch对Mongo中Stuff的变本，用于Search Stuff
 * @date 2023/10/19 19:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(indexName = "stuff", createIndex = true)
public class ESStuff {

    //ID
    @Id
    @Field(type = FieldType.Text)
    private String id;

    //物品名字
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;

    //物品属性
    @Field(type = FieldType.Keyword)
    private String attribute;

    //物品主色
    @Field(type = FieldType.Text)
    private String mainColor;

    //物品副色
    @Field(type = FieldType.Text)
    private String minorColor;

    //物品图片
    @Field(type = FieldType.Text)
    private String imgUrl;

    //物品描述
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String description;

    //是否标记
    @Field(type = FieldType.Boolean)
    private Boolean isBookmarks;

    //指向家具
    @Field(type = FieldType.Text)
    private String refFurnitureId;

    //指向房间
    @Field(type = FieldType.Text)
    private String refRoomId;

    //指向用户
    @Field(type = FieldType.Text)
    private String refUserId;

    //修改时间
    @Field(type = FieldType.Text)
    private String modifyTime;

    //修改次数
    @Field(type = FieldType.Integer)
    private Integer modifyCount;

}
