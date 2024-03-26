package com.yimingliao.mshivebackend.controller.version1.room;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.mongodb.Room;
import com.yimingliao.mshivebackend.service.mongodb.impl.RoomServiceImpl;
import com.yimingliao.mshivebackend.service.mongodb.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description 操作MongoDB中Room类的Controller层
 * @date 2023/10/18 22:12
 */
@CrossOrigin
@RestController
@RequestMapping("/v1/room")
@Slf4j
public class RoomController {

    @Autowired
    private RoomServiceImpl roomService;

    @Autowired
    private UserServiceImpl userService;

    //-----------------------------------------INSERT---------------------------------------
    //Insert One Room
    @PutMapping("/{user_uuid}/insert_one")
    public R insertOneRoom(@PathVariable("user_uuid") String userUUId, @RequestBody Room room) {
        if (userService.searchOneUserByUserUUId(userUUId).getStatus() != 200) {
            return R.error(403, "Insert Forbidden", new Date(), "无权限新增");
        }
        return roomService.insertOneRoom(room);
    }

    //-----------------------------------------UPDATE---------------------------------------
    //Update One Room
    @PatchMapping("/{user_uuid}/update_one")
    public R updateOneRoom(@PathVariable("user_uuid") String userUUId, @RequestBody Room room) {
        if (userService.searchOneUserByUserUUId(userUUId).getStatus() != 200) {
            return R.error(403, "Update Forbidden", new Date(), "无权限更新");
        }
        return roomService.updateOneRoom(room);
    }

    //-----------------------------------------DELETE---------------------------------------
    //Delete One User's Room, need roomUUId
    @DeleteMapping("/{user_uuid}/delete_one")
    public R deleteOneRoomByRoomUUId(@PathVariable("user_uuid") String userUUId,
                                     @RequestParam(name = "room_uuid") String roomUUId) {
        if (userService.searchOneUserByUserUUId(userUUId).getStatus() != 200) {
            return R.error(403, "Delete Forbidden", new Date(), "无权限删除");
        }
        return roomService.deleteOneRoomByRoomUUId(roomUUId);
    }

    //-----------------------------------------SEARCH---------------------------------------
    //Find One User's Some Rooms, need userUUId, lastSeenRoomId & searchSize
    @GetMapping("/{user_uuid}/search_list")
    public R searchRoomListByUserUUId(@PathVariable("user_uuid") String userUUId,
                                      @RequestParam(name = "last_seen_room_id") Long lastSeenRoomId,
                                      @RequestParam(name = "search_size") Integer searchSize) {
        if (userService.searchOneUserByUserUUId(userUUId).getStatus() != 200) {
            return R.error(403, "Search Forbidden", new Date(), "无权限搜索");
        }
        return roomService.searchRoomListByUserUUId(userUUId, lastSeenRoomId, searchSize);
    }

    //Find One User's All Rooms
    @GetMapping("/mshive-baskend/{user_uuid}/search_all")
    public R searchRoomAllByUserUUId(@PathVariable("user_uuid") String userUUId) {
        if (userService.searchOneUserByUserUUId(userUUId).getStatus() != 200) {
            return R.error(403, "Search Forbidden", new Date(), "无权限搜索");
        }
        return roomService.searchRoomAllByUserUUId(userUUId);
    }

    //Find One User's One Room, need userUUId & roomId
    @GetMapping("/{user_uuid}/search_one")
    public R searchOneRoomByUserUUId(@PathVariable("user_uuid") String userUUId,
                                     @RequestParam(name = "room_id") Long roomId) {
        if (userService.searchOneUserByUserUUId(userUUId).getStatus() != 200) {
            return R.error(403, "Search Forbidden", new Date(), "无权限搜索");
        }
        return roomService.searchOneRoomByUserUUId(userUUId, roomId);
    }

    //Find One User's One Room, need userUUId & roomId
    @PostMapping("/{user_uuid}/search_room_list_by_room_uuids")
    public R searchRoomByRoomUUId(@PathVariable("user_uuid") String userUUId,
                                  @RequestBody List<String> roomUUIds) {
        if (userService.searchOneUserByUserUUId(userUUId).getStatus() != 200) {
            return R.error(403, "Search Forbidden", new Date(), "无权限搜索");
        }
        return roomService.searchRoomListByRoomUUIdList(roomUUIds);
    }

    //Download One User's Optional Rom Report Form, need userUUId & JSON:RoomReportForm
    @PostMapping("/{user_uuid}/download_report_form")
    public ResponseEntity downloadOneUserRoomReportForm(@PathVariable("user_uuid") String userUUId,
                                                        @RequestParam(name = "start_date") String startDate,
                                                        @RequestParam(name = "end_date") String endDate,
                                                        @RequestParam(name = "only_bookmarks") Boolean onlyBookmarks,
                                                        @RequestParam(name = "need_all") Boolean needAll
    ) {
        if (userService.searchOneUserByUserUUId(userUUId).getStatus() != 200) {
            return null;
        }
        //roomReportForm
        try {
            return roomService.downloadOneUserRoomReportForm(userUUId, startDate, endDate, onlyBookmarks, needAll);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
