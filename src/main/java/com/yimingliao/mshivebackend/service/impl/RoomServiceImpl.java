package com.yimingliao.mshivebackend.service.impl;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.mongodb.Room;
import com.yimingliao.mshivebackend.mapper.mongodb.RoomRepository;
import com.yimingliao.mshivebackend.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public R deleteRoom(String roomId) {
        roomRepository.deleteById(roomId);
        return R.success(200, "Success", new Date());
    }

    @Override
    public R findRoomList(String startId, String endId) {
        return null;
    }

    @Override
    public R findRoomAll() {
        List<Room> roomList = roomRepository.findAll();
        if (roomList.isEmpty()){
            return R.error(404, "Failed", new Date());
        }
        return R.success(200, "Success", new Date(), roomList);
    }

    @Override
    public R findRoomById(String roomId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        //检查是否为空值
        if (!roomOptional.isPresent()){
            return R.error(404, "Failed", new Date());
        }
        Room room = roomOptional.get();
        return R.success(200, "Success", new Date(), room);
    }
}
