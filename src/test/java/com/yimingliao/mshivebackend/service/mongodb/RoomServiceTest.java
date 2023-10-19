package com.yimingliao.mshivebackend.service.mongodb;

import cn.hutool.core.date.DateTime;
import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.mongodb.Room;
import com.yimingliao.mshivebackend.mapper.mongodb.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description Room CRUD 测试类
 * @date 2023/10/17 22:50
 */
@SpringBootTest
public class RoomServiceTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    //ADD ROOM
    @Test
    public void addRoom() {
        Room room = new Room();
        room.setRefUserId("648fdfbb54b0950d00c37c2e");
        //room.setRoomId(1000000004L);
        room.setName("超大书房");
        room.setAttribute("浴室");
        room.setMainColor("#ffaaaa");
        room.setMinorColor("#aaffff");
        room.setDescription("无");
        room.setIsBookmarks(false);
        room.setIsDeleted(false);
        room.setModifyTime(new DateTime().toLocalDateTime());
        room.setModifyCount(1);
        //保存新增的房间
        Room saveRoom = roomRepository.save(room);
        //判定是否保存成功
        if ("".equals(saveRoom.getId())) {
            System.out.println(R.error(404, "Failed", new Date()));
        }
        System.out.println(R.success(200, "Success", new Date()));
    }

    //UPDATE ROOM
    @Test
    public void updateRoom() {
        String roomUUId = "6530a5dc4bb3f3606b9a13ef";
        Room room = mongoTemplate.findById(roomUUId, Room.class, "room");
        System.out.println("room: " + room);
        if (room == null) {
            System.out.println(R.error(404, "Failed", new Date()));
        } else {
            room.setRefUserId("648fdfbb54b0950d00c37c2f");
            room.setName("大厨房大大大");
            room.setAttribute("大厨房");
            room.setDescription("无内容");
            room.setIsBookmarks(true);
            room.setIsDeleted(true);
            room.setModifyTime(new DateTime().toLocalDateTime());
            room.setModifyCount(1);
            //保存新增的房间
            Room saveRoom = roomRepository.save(room);
            //判定是否保存成功
            if ("".equals(saveRoom.getId())) {
                System.out.println(R.error(404, "Failed", new Date()));
            }
            System.out.println(R.success(200, "Success", new Date()));
        }
    }

    //Delete One User's Room, need roomUUId
    @Test
    public void deleteRoom() {
        String roomUUId = "6530a456f1f6b323f7250f53";
        roomRepository.deleteById(roomUUId);
        System.out.println(R.success(200, "Success", new Date()));
    }

    //Find One User's Some Rooms, need userUUId, lastSeenRoomId & searchSize
    @Test
    public void findRoomList() {
        String userUUId = "648fdfbb54b0950d00c37c2e";
        Long lastSeenRoomId = 12L;
        int searchSize = 3;
        Query query = new Query();
        //define the start ID using addCriteria
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        query.addCriteria(Criteria.where("room_id").gt(lastSeenRoomId));
        query.limit(searchSize);
        System.out.println(query);
        //Sort
        //Sort sort = Sort.by(order);
        //query.with(Sort.by(Sort.Direction.DESC))
        List<Room> roomList = mongoTemplate.find(query, Room.class);
        System.out.println(R.success(200, "Success", new Date(), roomList));
    }

    //Find One User's All Rooms
    @Test
    public void findRoomAll() {
        String userUUId = "648fdfbb54b0950d00c37c2e";
        Query query = new Query();
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        List<Room> roomList = mongoTemplate.find(query, Room.class);
        if (roomList.isEmpty()) {
            System.out.println(R.error(404, "Failed", new Date()));
        }
        System.out.println(R.success(200, "Success", new Date(), roomList));
    }

    //Find One User's One Room
    @Test
    public void findRoomById() {
        String userUUId = "648fdfbb54b0950d00c37c2e";
        String roomId = "1000000002";
        Query query = new Query();
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        query.addCriteria(Criteria.where("room_id").is(roomId));
        List<Room> roomList = mongoTemplate.find(query, Room.class);
        if (roomList.isEmpty()) {
            System.out.println(R.error(404, "Failed", new Date()));
        }
        System.out.println(R.success(200, "Success", new Date(), roomList));
    }

}
