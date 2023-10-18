package com.yimingliao.mshivebackend.service.mongodb.impl;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.mongodb.Room;
import com.yimingliao.mshivebackend.mapper.mongodb.RoomRepository;
import com.yimingliao.mshivebackend.service.mongodb.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Calendo
 * @version 1.0
 * @description Room 业务层实现类
 * @date 2023/10/16 21:15
 */
@Service
public class RoomServiceImpl implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    //Add One Room
    @Override
    public R addRoom(Room room) {
        //保存新增的房间
        Room saveRoom = roomRepository.save(room);
        //判定是否保存成功
        if ("".equals(saveRoom.getId())) {
            return R.error(404, "Failed", new Date());
        }
        return R.success(200, "Success", new Date());
    }

    //Update One Room
    @Override
    public R updateRoom(Room room) {
        //保存新增的房间
        Room saveRoom = roomRepository.save(room);
        //判定是否保存成功
        if ("".equals(saveRoom.getId())) {
            return R.error(404, "Failed", new Date());
        }
        return R.success(200, "Success", new Date());
    }

    //Delete One User's Room, need roomUUId
    @Override
    public R deleteRoom(String roomUUId) {
        roomRepository.deleteById(roomUUId);
        return R.success(200, "Success", new Date());
    }

    //Find One User's Some Rooms, need userUUId, lastSeenRoomId & searchSize
    @Override
    public R findRoomList(String userUUId, String lastSeenRoomId, Integer searchSize) {
        Query query = new Query();
        //define the start ID using addCriteria
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        query.addCriteria(Criteria.where("room_id").gt(lastSeenRoomId));
        query.limit(searchSize);
        //Sort
        //Sort sort = Sort.by(order);
        //query.with(Sort.by(Sort.Direction.DESC))
        List<Room> roomList = mongoTemplate.find(query, Room.class);
        return R.success(200, "Success", new Date(), roomList);
    }

    //Find One User's All Rooms
    @Override
    public R findRoomAll(String userUUId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        List<Room> roomList = mongoTemplate.find(query, Room.class);
        if (roomList.isEmpty()) {
            return R.error(404, "Failed", new Date());
        }
        return R.success(200, "Success", new Date(), roomList);
    }

    //Find One User's One Room
    @Override
    public R findRoomById(String userUUId, String roomId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        query.addCriteria(Criteria.where("room_id").is(roomId));
        List<Room> roomList = mongoTemplate.find(query, Room.class);
        if (roomList.isEmpty()) {
            return R.error(404, "Failed", new Date());
        }
        return R.success(200, "Success", new Date(), roomList);
    }
}
