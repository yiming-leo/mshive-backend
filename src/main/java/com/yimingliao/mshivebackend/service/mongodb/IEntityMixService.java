package com.yimingliao.mshivebackend.service.mongodb;

import com.yimingliao.mshivebackend.common.R;

/**
 * @author Calendo
 * @version 1.0
 * @description 混合实体类操作
 * @date 2023/10/19 15:24
 */
public interface IEntityMixService {

    //--------------------Room--Furniture Related--------------------

    //Find One User's Furniture by his UserUUId & RoomUUId
    R searchOneFurnitureByUserUUIdRoomUUId(String userUUId, String roomUUId);

}
