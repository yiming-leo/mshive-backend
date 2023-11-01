package com.yimingliao.mshivebackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description 返回给前端的bookmark功能的树形结构
 * @date 2023/10/31 22:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BookmarkTreeVO {

    private String name;

    private String id;

    private String mainColor;

    private String minorColor;

    private Boolean isBookmarks;

    private String attribute;

    private String image;

    //父级ID
    private String parentId;

    //子集
    private List<BookmarkTreeVO> children;

    public BookmarkTreeVO(String name, String id, String mainColor, String minorColor, Boolean isBookmarks, String attribute, String image, String parentId) {
        this.name = name;
        this.id = id;
        this.mainColor = mainColor;
        this.minorColor = minorColor;
        this.isBookmarks = isBookmarks;
        this.attribute = attribute;
        this.image = image;
        this.parentId = parentId;
    }
}
