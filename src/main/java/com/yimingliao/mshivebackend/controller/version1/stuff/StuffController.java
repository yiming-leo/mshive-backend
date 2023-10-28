package com.yimingliao.mshivebackend.controller.version1.stuff;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.mongodb.Stuff;
import com.yimingliao.mshivebackend.service.mongodb.impl.StuffServiceImpl;
import com.yimingliao.mshivebackend.service.mongodb.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author Calendo
 * @version 1.0
 * @description TODO
 * @date 2023/10/28 18:52
 */
@RestController
@RequestMapping("/v1/stuff")
@Slf4j
public class StuffController {

    @Autowired
    private StuffServiceImpl stuffService;

    @Autowired
    private UserServiceImpl userService;

    //Insert One Stuff
    @PutMapping("/{user_uuid}/insert_one")
    public R insertOneStuff(@PathVariable("user_uuid") String userUUId,
                                @RequestBody Stuff stuff) {
        if (userService.searchOneUserByUserUUId(userUUId).getStatus() != 200) {
            return R.error(403, "Insert Forbidden", new Date(), "无权限新增");
        }
        return stuffService.insertOneStuff(stuff);
    }

    //Update One Stuff
    @PatchMapping("/{user_uuid}/update_one")
    public R updateOneStuff(@PathVariable(name = "user_uuid") String userUUId,
                                @RequestBody Stuff stuff) {
        if (userService.searchOneUserByUserUUId(userUUId).getStatus() != 200) {
            return R.error(403, "Update Forbidden", new Date(), "无权限修改");
        }
        return stuffService.updateOneStuff(stuff);
    }

    //Delete One User's Stuff, need stuffUUId
    @DeleteMapping("/{user_uuid}/delete_one")
    public R deleteOneStuffByStuffUUId(@PathVariable(name = "user_uuid") String userUUId,
                                               @RequestParam(name = "stuff_uuid") String stuffUUId) {
        if (userService.searchOneUserByUserUUId(userUUId).getStatus() != 200) {
            return R.error(403, "Delete Forbidden", new Date(), "无权限删除");
        }
        return stuffService.deleteOneStuffByStuffUUId(stuffUUId);
    }

    //Find One User's Some Stuff, need userUUId, lastSeenStuffId & searchSize
    @GetMapping("/{user_uuid}/search_list")
    public R searchStuffListByUserUUId(@PathVariable(name = "user_uuid") String userUUId,
                                           @RequestParam(name = "last_seen_stuff_id") Long lastSeenStuffId,
                                           @RequestParam(name = "search_size") Integer searchSize) {
        if (userService.searchOneUserByUserUUId(userUUId).getStatus() != 200) {
            return R.error(403, "Search Forbidden", new Date(), "无权限搜索");
        }
        return stuffService.searchStuffListByUserUUId(userUUId, lastSeenStuffId, searchSize);
    }

    //Find One User's All Stuff
    @GetMapping("/{user_uuid}/search_all")
    public R searchStuffAllByUserUUId(@PathVariable(name = "user_uuid") String userUUId) {
        if (userService.searchOneUserByUserUUId(userUUId).getStatus() != 200) {
            return R.error(403, "Search Forbidden", new Date(), "无权限搜索");
        }
        return stuffService.searchStuffAllByUserUUId(userUUId);
    }

    //Find One User's One Stuff, need userUUId & stuffId
    @GetMapping("/{user_uuid}/search_one")
    public R searchOneStuffByUserUUId(@PathVariable(name = "user_uuid") String userUUId,
                                          @RequestParam(name = "stuff_id") Long stuffId) {
        if (userService.searchOneUserByUserUUId(userUUId).getStatus() != 200) {
            return R.error(403, "Search Forbidden", new Date(), "无权限搜索");
        }
        return stuffService.searchOneStuffByUserUUId(userUUId, stuffId);
    }

    //Download One User's Optional Rom Report Form, need userUUId & JSON:RoomReportForm
    @PostMapping("/{user_uuid}/download_report_form")
    public R downloadOneUserRoomReportForm(HttpServletResponse response,
                                           @PathVariable("user_uuid") String userUUId,
                                           @RequestParam(name = "start_date") String startDate,
                                           @RequestParam(name = "end_date") String endDate,
                                           @RequestParam(name = "only_bookmarks") Boolean onlyBookmarks,
                                           @RequestParam(name = "need_all") Boolean needAll
    ) {
        if (userService.searchOneUserByUserUUId(userUUId).getStatus() != 200) {
            return R.error(403, "Download Forbidden", new Date(), "无权限下载");
        }
        //stuffReportForm
        try {
            return stuffService.downloadOneUserStuffReportForm(response, userUUId, startDate, endDate, onlyBookmarks, needAll);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
