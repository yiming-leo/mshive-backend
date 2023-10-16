package com.yimingliao.mshivebackend.entity.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


@Document(collection = "room")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    //mongodb自动生成的主键，与_id对应
    @Id
    private String id;

    //增量ID，加快索引
    @Indexed
    @Field("room_id")
    private String roomId;

    //房间名称
    @Field("name")
    private String name;

    //房间属性
    @Field("attribute")
    private String attribute;

    //主颜色
    @Field("main_color")
    private String mainColor;

    //副颜色
    @Field("minor_color")
    private String minorColor;

    //图片地址
    @Field("img_url")
    private String imgUrl;

    //描述
    @Field("description")
    private String description;

    //标记
    @Field("is_bookmarks")
    private Boolean isBookmarks;

    //修改次数
    @Field("modify_count")
    private Integer modifyCount;

    //上次修改时间
    @Field("modify_time")
    private LocalDateTime modifyTime;

    //是否已删除
    @Field("is_deleted")
    private Boolean isDeleted;

    //用户归属指向
    @Field("ref_user_id")
    private String refUserId;

    public Room(String id, String roomId, String name,
                String attribute, String mainColor,
                String minorColor, String imgUrl,
                String description, Boolean isBookmarks,
                Integer modifyCount, LocalDateTime modifyTime,
                Boolean isDeleted, String refUserId) {
        this.id = id;
        this.roomId = roomId;
        this.name = name;
        this.attribute = attribute;
        this.mainColor = mainColor;
        this.minorColor = minorColor;
        this.imgUrl = imgUrl;
        this.description = description;
        this.isBookmarks = isBookmarks;
        this.modifyCount = modifyCount;
        this.modifyTime = modifyTime;
        this.isDeleted = isDeleted;
        this.refUserId = refUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getMainColor() {
        return mainColor;
    }

    public void setMainColor(String mainColor) {
        this.mainColor = mainColor;
    }

    public String getMinorColor() {
        return minorColor;
    }

    public void setMinorColor(String minorColor) {
        this.minorColor = minorColor;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getBookmarks() {
        return isBookmarks;
    }

    public void setBookmarks(Boolean bookmarks) {
        isBookmarks = bookmarks;
    }

    public Integer getModifyCount() {
        return modifyCount;
    }

    public void setModifyCount(Integer modifyCount) {
        this.modifyCount = modifyCount;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getRefUserId() {
        return refUserId;
    }

    public void setRefUserId(String refUserId) {
        this.refUserId = refUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(roomId, room.roomId) && Objects.equals(name, room.name) && Objects.equals(attribute, room.attribute) && Objects.equals(mainColor, room.mainColor) && Objects.equals(minorColor, room.minorColor) && Objects.equals(imgUrl, room.imgUrl) && Objects.equals(description, room.description) && Objects.equals(isBookmarks, room.isBookmarks) && Objects.equals(modifyCount, room.modifyCount) && Objects.equals(modifyTime, room.modifyTime) && Objects.equals(isDeleted, room.isDeleted) && Objects.equals(refUserId, room.refUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomId, name, attribute, mainColor, minorColor, imgUrl, description, isBookmarks, modifyCount, modifyTime, isDeleted, refUserId);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", roomId='" + roomId + '\'' +
                ", name='" + name + '\'' +
                ", attribute='" + attribute + '\'' +
                ", mainColor='" + mainColor + '\'' +
                ", minorColor='" + minorColor + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", description='" + description + '\'' +
                ", isBookmarks=" + isBookmarks +
                ", modifyCount=" + modifyCount +
                ", modifyTime=" + modifyTime +
                ", isDeleted=" + isDeleted +
                ", refUserId='" + refUserId + '\'' +
                '}';
    }
}
