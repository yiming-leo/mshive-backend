package com.yimingliao.mshivebackend.controller.version1.furniture;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.mongodb.Furniture;
import com.yimingliao.mshivebackend.service.mongodb.impl.FurnitureServiceImpl;
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
 * @description 操作MongoDB中Furniture类的Controller层
 * @date 2023/10/18 22:12
 */
@RestController
@RequestMapping("/v1/furniture")
@Slf4j
public class FurnitureController {

    @Autowired
    private FurnitureServiceImpl furnitureService;

    @Autowired
    private UserServiceImpl userService;

    //Insert One Furniture
    @PutMapping("/insert_one")
    public R insertOneFurniture(@RequestBody Furniture furniture) {
        return furnitureService.insertOneFurniture(furniture);
    }

    //Update One Furniture
    @PatchMapping("/update_one")
    public R updateOneFurniture(@RequestBody Furniture furniture) {
        return furnitureService.updateOneFurniture(furniture);
    }

    //Delete One User's Furniture, need furnitureUUId
    @DeleteMapping("/delete_one")
    public R deleteOneFurnitureByFurnitureUUId(@RequestParam(name = "furnitureUUId") String furnitureUUId) {
        return furnitureService.deleteOneFurnitureByFurnitureUUId(furnitureUUId);
    }

    //Find One User's Some Furniture, need userUUId, lastSeenFurnitureId & searchSize
    @GetMapping("/search_list")
    public R searchFurnitureListByUserUUId(@RequestParam(name = "userUUId") String userUUId,
                                      @RequestParam(name = "lastSeenFurnitureId") Long lastSeenFurnitureId,
                                      @RequestParam(name = "searchSize") Integer searchSize) {
        return furnitureService.searchFurnitureListByUserUUId(userUUId, lastSeenFurnitureId, searchSize);
    }

    //Find One User's All Furniture
    @GetMapping("/search_all")
    public R searchFurnitureAllByUserUUId(@RequestParam(name = "userUUId") String userUUId) {
        return furnitureService.searchFurnitureAllByUserUUId(userUUId);
    }

    //Find One User's One Furniture, need userUUId & furnitureId
    @GetMapping("/search_one")
    public R searchOneFurnitureByUserUUId(@RequestParam(name = "userUUId") String userUUId,
                                     @RequestParam(name = "furnitureId") Long furnitureId) {
        return furnitureService.searchOneFurnitureByUserUUId(userUUId, furnitureId);
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
        //roomReportForm
        try {
            return furnitureService.downloadOneUserFurnitureReportForm(response, userUUId, startDate, endDate, onlyBookmarks, needAll);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
