package com.yimingliao.mshivebackend.service.mongodb;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.mongodb.Room;
import org.springframework.data.domain.Pageable;

/**
 * @author Calendo
 * @version 1.0
 * @description Room 业务层接口
 * @date 2023/10/16 21:15
 */
public interface IRoomService {

    //Add One Room
    R addRoom(Room room);

    //Update One Room
    R updateRoom(Room room);

    //Delete One User's Room, need roomUUId
    R deleteRoom(String roomUUId);

    //Find One User's Some Rooms, need lastSeenRoomId & searchSize
    R findRoomList(String userUUId, String startId, Integer searchSize);

    //Find One User's All Rooms
    R findRoomAll(String userUUId);

    //Find One User's One Room, need userUUId & roomId
    R findRoomById(String userUUId, String roomId);

}
