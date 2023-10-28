package com.yimingliao.mshivebackend.dto;

import com.yimingliao.mshivebackend.entity.mongodb.Furniture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description 这是对分页查询Furniture List的一个返回封装，其中包含了原有的List<Room>,还有lastSeenRoomId,searchSize
 * @date 2023/10/28 12:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FurnitureScrollListDTO {

    private Long lastSeenFurnitureId;

    private Integer searchSize;

    private List<Furniture> furnitureList;

}
