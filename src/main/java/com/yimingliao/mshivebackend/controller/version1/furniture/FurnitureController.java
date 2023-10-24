package com.yimingliao.mshivebackend.controller.version1.furniture;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.mongodb.Furniture;
import com.yimingliao.mshivebackend.service.mongodb.impl.FurnitureServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
