package com.yimingliao.mshivebackend.service.mongodb.impl;

import com.mongodb.client.result.UpdateResult;
import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.mongodb.Room;
import com.yimingliao.mshivebackend.mapper.mongodb.RoomRepository;
import com.yimingliao.mshivebackend.service.mongodb.IRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description Room 业务层实现类
 * @date 2023/10/16 21:15
 */
@Service
@Slf4j
public class RoomServiceImpl implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    //Insert One Room
    @Override
    public R insertOneRoom(Room room) {
        room.setModifyCount(1);
        room.setModifyTime(new Date().toString());
        room.setIsDeleted(false);
        //保存新增的房间
        Room saveRoom = roomRepository.save(room);
        log.info("insertOneRoom: " + room);
        //判定是否保存成功
        if ("".equals(saveRoom.getId())) {
            return R.error(404, "Failed", new Date());
        }
        return R.success(200, "Success", new Date());
    }

    //Update One Room
    @Override
    public R updateOneRoom(Room room) {
        //新建query
        Query query = new Query();
        //根据uuid查找room，这里where随便是id还是_id都可以，但是前端返回来的JSON必须和后端的Entity类相符合！
        query.addCriteria(Criteria.where("id").is(room.getId()));
        //更新room
        Update update = new Update()
                .set("name", room.getName())
                .set("attribute", room.getAttribute())
                .set("mainColor", room.getMainColor())
                .set("minorColor", room.getMinorColor())
                .set("imgUrl", room.getImgUrl())
                .set("description", room.getDescription())
                .set("isBookmarks", room.getIsBookmarks())
                .set("refUserId", room.getRefUserId())
                .set("modifyTime", new Date().toString());
        //保存新增的房间
        log.info("updateOneRoom: " + update);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Room.class);
        long modifiedCount = updateResult.getModifiedCount();
        //判定是否保存成功
        if (updateResult.getModifiedCount() == 0) {
            return R.error(404, "Update Failed", new Date(), modifiedCount);
        }
        return R.success(200, "Update Success", new Date(), modifiedCount);
    }

    //Delete One User's Room, need roomUUId
    @Override
    public R deleteOneRoomByRoomUUId(String roomUUId) {
        roomRepository.deleteById(roomUUId);
        log.info("deleteOneRoomByRoomUUId success");
        return R.success(200, "Success", new Date());
    }

    //Find One User's Some Rooms, need userUUId, lastSeenRoomId & searchSize
    @Override
    public R searchRoomListByUserUUId(String userUUId, Long lastSeenRoomId, Integer searchSize) {
        Query query = new Query();
        //define the start ID using addCriteria
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        query.addCriteria(Criteria.where("room_id").gt(lastSeenRoomId));
        query.limit(searchSize);
        log.info("Room List Search Query: " + query);
        //Sort
        //Sort sort = Sort.by(order);
        //query.with(Sort.by(Sort.Direction.DESC))
        List<Room> roomList = mongoTemplate.find(query, Room.class);
        log.info("searchRoomListByUserUUId: " + roomList);
        return R.success(200, "Success", new Date(), roomList);
    }

    //Find One User's All Rooms
    @Override
    public R searchRoomAllByUserUUId(String userUUId) {
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
    public R searchOneRoomByUserUUId(String userUUId, Long roomId) {
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
