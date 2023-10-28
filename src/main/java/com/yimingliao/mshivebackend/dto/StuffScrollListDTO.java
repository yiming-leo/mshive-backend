package com.yimingliao.mshivebackend.dto;

import com.yimingliao.mshivebackend.entity.mongodb.Stuff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description 这是对分页查询Stuff List的一个返回封装，其中包含了原有的List ,还有lastSeenStuffId,searchSize
 * @date 2023/10/28 18:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StuffScrollListDTO {

    private Long lastSeenStuffId;

    private Integer searchSize;

    private List<Stuff> stuffList;

}
