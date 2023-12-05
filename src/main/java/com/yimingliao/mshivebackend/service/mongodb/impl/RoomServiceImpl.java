package com.yimingliao.mshivebackend.service.mongodb.impl;

import com.mongodb.client.result.UpdateResult;
import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.dto.RoomScrollListDTO;
import com.yimingliao.mshivebackend.entity.mongodb.Room;
import com.yimingliao.mshivebackend.entity.report.FurnitureReportForm;
import com.yimingliao.mshivebackend.mapper.mongodb.RoomRepository;
import com.yimingliao.mshivebackend.service.mongodb.IRoomService;
import com.yimingliao.mshivebackend.utils.ReportFormWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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

    @Autowired
    private ReportFormWriter reportFormWriter;

    //----------------------------------INSERT----------------------------------
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
            return R.error(404, "Insert Failed", new Date());
        }
        return R.success(200, "Insert Success", new Date(), saveRoom.getId());
    }

    //----------------------------------UPDATE----------------------------------
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
                .set("main_color", room.getMainColor())
                .set("minor_color", room.getMinorColor())
                .set("img_url", room.getImgUrl())
                .set("description", room.getDescription())
                .set("is_bookmarks", room.getIsBookmarks())
                .set("ref_user_id", room.getRefUserId())//对所属User的索引
                .set("modify_time", new Date().toString())//处理时间：后端插入时间
                .set("modify_count", room.getModifyCount() + 1)//处理修改次数：+1
                .set("is_deleted", false);
        //保存新增的房间
        log.info("updateOneRoom: " + update);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Room.class);
        //判定是否保存成功
        if (updateResult.getModifiedCount() == 0) {
            return R.error(404, "Update Failed", new Date(), room.getId());
        }
        return R.success(200, "Update Success", new Date(), room.getId());
    }

    //----------------------------------DELETE----------------------------------
    //Delete One User's Room, need roomUUId
    @Override
    public R deleteOneRoomByRoomUUId(String roomUUId) {
        roomRepository.deleteById(roomUUId);
        log.info("deleteOneRoomByRoomUUId success: " + roomUUId);
        return R.success(200, "Delete Success", new Date(), roomUUId);
    }

    //----------------------------------SEARCH----------------------------------
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
        //装载到RoomScrollListDTO内
        RoomScrollListDTO roomScrollListDTO = new RoomScrollListDTO();
        roomScrollListDTO.setLastSeenRoomId(lastSeenRoomId);
        roomScrollListDTO.setRoomList(roomList);
        roomScrollListDTO.setSearchSize(searchSize);
        log.info("searchRoomListByUserUUId: " + roomScrollListDTO);
        return R.success(200, "Search Success", new Date(), roomScrollListDTO);
    }

    //Find One User's All Rooms
    @Override
    public R searchRoomAllByUserUUId(String userUUId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        List<Room> roomList = mongoTemplate.find(query, Room.class);
        if (roomList.isEmpty()) {
            return R.error(404, "Search Failed", new Date(), "No Massage Matched");
        }
        return R.success(200, "Search Success", new Date(), roomList);
    }

    //Find One User's One Room
    @Override
    public R searchOneRoomByUserUUId(String userUUId, Long roomId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        query.addCriteria(Criteria.where("room_id").is(roomId));
        List<Room> roomList = mongoTemplate.find(query, Room.class);
        if (roomList.isEmpty()) {
            return R.error(404, "Search Failed", new Date());
        }
        return R.success(200, "Search Success", new Date(), roomList);
    }

    //Search RoomList By RoomUUId List***EXTRA***
    @Override
    public R searchRoomListByRoomUUIdList(List<String> roomUUIds) {
        List<Room> roomListResult = new ArrayList<>();
        for (String roomUUId : roomUUIds) {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(roomUUId));
            List<Room> roomList = mongoTemplate.find(query, Room.class);
            if (roomList.isEmpty()) {
                return R.error(404, "Search Failed", new Date());
            }
            roomListResult.add(roomList.get(0));
        }
        return R.success(200, "Search Success", new Date(), roomListResult);
    }

    //----------------------------------DOWNLOAD----------------------------------
    //Download One User's Optional Room Report
    @Override
    public ResponseEntity downloadOneUserRoomReportForm(String userUUId, String startDate,
                                                        String endDate, Boolean onlyBookmarks,
                                                        Boolean needAll) throws IOException {
        //查询条件
        Query query = new Query();
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        query.addCriteria(Criteria.where("is_bookmarks").is(onlyBookmarks));
        if (!needAll) {
            query.addCriteria(Criteria.where("modify_time").gte(startDate).lte(endDate));
        }
        List<Room> roomList = mongoTemplate.find(query, Room.class);
        log.info(roomList.toString());
        if (roomList.isEmpty()) {
            return null;
        }

        //交给报表生成工具类处理
        ResponseEntity responseEntity = reportFormWriter.excelReportForm(roomList, FurnitureReportForm.class);
        log.info(responseEntity.toString());
        return responseEntity;
    }
}
