package com.yimingliao.mshivebackend.controller.mongodb;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.mongodb.Room;
import com.yimingliao.mshivebackend.service.mongodb.impl.RoomServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author Calendo
 * @version 1.0
 * @description 操作MongoDB中Room类的Controller层
 * @date 2023/10/18 22:12
 */
@RestController
@RequestMapping("/room")
@Slf4j
public class RoomController {

    @Autowired
    private RoomServiceImpl roomService;

    //Insert One Room
    @PutMapping("/insert_one")
    public R insertOneRoom(@RequestBody Room room) {
        return roomService.insertOneRoom(room);
    }

    //Update One Room
    @PatchMapping("/update_one")
    public R updateOneRoom(@RequestBody Room room) {
        return roomService.updateOneRoom(room);
    }

    //Delete One User's Room, need roomUUId
    @DeleteMapping("/delete_one")
    public R deleteOneRoomByRoomUUId(@RequestParam(name = "roomUUId") String roomUUId) {
        return roomService.deleteOneRoomByRoomUUId(roomUUId);
    }

    //Find One User's Some Rooms, need userUUId, lastSeenRoomId & searchSize
    @GetMapping("/search_list")
    public R searchRoomListByUserUUId(@RequestParam(name = "userUUId") String userUUId,
                                      @RequestParam(name = "lastSeenRoomId") Long lastSeenRoomId,
                                      @RequestParam(name = "searchSize") Integer searchSize) {
        return roomService.searchRoomListByUserUUId(userUUId, lastSeenRoomId, searchSize);
    }

    //Find One User's All Rooms
    @GetMapping("/search_all")
    public R searchRoomAllByUserUUId(@RequestParam(name = "userUUId") String userUUId) {
        return roomService.searchRoomAllByUserUUId(userUUId);
    }

    //Find One User's One Room, need userUUId & roomId
    @GetMapping("/search_one")
    public R searchOneRoomByUserUUId(@RequestParam(name = "userUUId") String userUUId,
                                     @RequestParam(name = "roomId") Long roomId) {
        return roomService.searchOneRoomByUserUUId(userUUId, roomId);
    }
}