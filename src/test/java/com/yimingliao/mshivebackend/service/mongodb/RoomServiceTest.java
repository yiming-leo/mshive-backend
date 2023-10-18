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
 * @description TODO
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
        String roomUUId = "652ea5f5dd4b917c52083f6e";
        Room room = mongoTemplate.findById(roomUUId, Room.class);
        if (room == null) {
            System.out.println(R.error(404, "Failed", new Date()));
        } else {
            room.setRefUserId("648fdfbb54b0950d00c37c2f");
            room.setName("大厨房");
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
        String roomUUId = "652f5d1df85c172e4c75b54e";
        roomRepository.deleteById(roomUUId);
        System.out.println(R.success(200, "Success", new Date()));
    }

    //Find One User's Some Rooms, need userUUId, lastSeenRoomId & searchSize
    @Test
    public void findRoomList() {
        String userUUId = "648fdfbb54b0950d00c37c2e";
        String lastSeenRoomId = "1000000003";
        int searchSize = 2;
        Query query = new Query();
        //define the start ID using addCriteria
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        query.addCriteria(Criteria.where("room_id").gt(lastSeenRoomId));
        query.limit(searchSize);
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
