package com.yimingliao.mshivebackend.controller.version1.additive;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.mongodb.Furniture;
import com.yimingliao.mshivebackend.entity.mongodb.Room;
import com.yimingliao.mshivebackend.entity.mongodb.Stuff;
import com.yimingliao.mshivebackend.entity.mongodb.User;
import com.yimingliao.mshivebackend.service.mongodb.impl.FurnitureServiceImpl;
import com.yimingliao.mshivebackend.service.mongodb.impl.RoomServiceImpl;
import com.yimingliao.mshivebackend.service.mongodb.impl.StuffServiceImpl;
import com.yimingliao.mshivebackend.service.mongodb.impl.UserServiceImpl;
import com.yimingliao.mshivebackend.utils.BookmarkTreeUtil;
import com.yimingliao.mshivebackend.vo.BookmarkTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description TODO
 * @date 2023/10/31 23:52
 */
@RestController
@RequestMapping("/v1/bk_tree")
public class BookmarkTreeController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoomServiceImpl roomService;

    @Autowired
    private FurnitureServiceImpl furnitureService;

    @Autowired
    private StuffServiceImpl stuffService;

    @GetMapping("/{user_uuid}/search_tree")
    public R treeTest(@PathVariable("user_uuid") String userUUId) {
        R r = userService.searchOneUserByUserUUId(userUUId);
        if (r.getStatus() != 200) {
            return R.error(403, "Insert Forbidden", new Date(), "无权限新增");
        }
        User data = (User) r.getData();
        String username = data.getUsername();
        // 模拟测试数据（通常为数据库的查询结果）
        List<BookmarkTreeVO> treeNodeList = new ArrayList<>();
        //装载mock
        //首先需要装载Root对象
        treeNodeList.add(new BookmarkTreeVO(username, userUUId, "#000000",
                "#000000", false, "", "", ""));
        //其次再装载内部物品
        //room系列装载
        List<Room> roomList = (List<Room>) roomService.searchRoomAllByUserUUId(userUUId).getData();
        for (Room room : roomList) {
            treeNodeList.add(new BookmarkTreeVO(room.getName(), room.getId(),
                    room.getMainColor(), room.getMinorColor(), room.getIsBookmarks(),
                    room.getAttribute(), room.getImgUrl(), room.getRefUserId()));
        }
        //furniture系列装载
        List<Furniture> furnitureList = (List<Furniture>) furnitureService.searchFurnitureAllByUserUUId(userUUId).getData();
        for (Furniture furniture : furnitureList) {
            treeNodeList.add(new BookmarkTreeVO(furniture.getName(), furniture.getId(),
                    furniture.getMainColor(), furniture.getMinorColor(), furniture.getIsBookmarks(),
                    furniture.getAttribute(), furniture.getImgUrl(), furniture.getRefRoomId()));
        }
        //stuff系列装载
        List<Stuff> stuffList = (List<Stuff>) stuffService.searchStuffAllByUserUUId(userUUId).getData();
        for (Stuff stuff : stuffList) {
            treeNodeList.add(new BookmarkTreeVO(stuff.getName(), stuff.getId(),
                    stuff.getMainColor(), stuff.getMinorColor(), stuff.getIsBookmarks(),
                    stuff.getAttribute(), stuff.getImgUrl(), stuff.getRefRoomId()));
        }
        // 创建树形结构（数据集合作为参数）
        BookmarkTreeUtil treeBuild = new BookmarkTreeUtil(treeNodeList);
        // 原查询结果转换树形结构
        treeNodeList = treeBuild.buildTree();
        //return
        return R.success(200, "Tree Build Success", new Date(), treeNodeList);
    }

}
