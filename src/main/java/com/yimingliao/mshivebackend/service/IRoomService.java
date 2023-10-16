package com.yimingliao.mshivebackend.service;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.mongodb.Room;

/**
 * @author Calendo
 * @version 1.0
 * @description Room 业务层接口
 * @date 2023/10/16 21:15
 */
public interface IRoomService {

    R addRoom(Room room);

    R updateRoom(Room room);

    R deleteRoom(String roomId);

    R findRoomList(String startId, String endId);

    R findRoomAll();

    R findRoomById(String roomId);

}
