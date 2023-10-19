package com.yimingliao.mshivebackend.service.mongodb;

import cn.hutool.core.date.DateTime;
import com.yimingliao.mshivebackend.entity.mongodb.Furniture;
import com.yimingliao.mshivebackend.service.mongodb.impl.FurnitureServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Calendo
 * @version 1.0
 * @description FFurniture CRUD 测试类
 * @date 2023/10/19 15:46
 */
@SpringBootTest
public class FurnitureServiceTest {

    @Autowired
    private FurnitureServiceImpl furnitureService;

    @Test
    public void addFurniture() {
        Furniture furniture = new Furniture();
        furniture.setRefUserId("648fdfbb54b0950d00c37c2e");
        furniture.setRefRoomId("652ea5f5dd4b917c52083f6e");
        furniture.setName("小板凳");
        furniture.setAttribute("小家具");
        furniture.setMainColor("#ffaaaa");
        furniture.setMinorColor("#aaffff");
        furniture.setDescription("无");
        furniture.setIsBookmarks(false);
        furniture.setIsDeleted(false);
        furniture.setModifyTime(new DateTime().toLocalDateTime());
        furniture.setModifyCount(1);
        System.out.println(furnitureService.insertOneFurniture(furniture));
    }

    @Test
    public void updateOneFurniture() {
        Furniture furniture = new Furniture();
        String furnitureUUId = "6530e01bebca500eb5635c0d";
        furniture.setId(furnitureUUId);

        furniture.setRefUserId("648fdfbb54b0950d00c37c2f");
        furniture.setRefRoomId("652e9e409277392305fe959f");
        furniture.setName("大板凳");
        furniture.setAttribute("大家具");
        furniture.setMainColor("#ffaa11");
        furniture.setMinorColor("#aa11ff");
        furniture.setDescription("无");
        furniture.setIsBookmarks(true);
        System.out.println(furnitureService.updateOneFurniture(furniture));
    }

    @Test
    public void deleteOneFurnitureByFurnitureUUId() {
        String furnitureUUId = "6530e29b998fc54e8b6c72ad";
        System.out.println(furnitureService.deleteOneFurnitureByFurnitureUUId(furnitureUUId));
    }

    @Test
    public void searchFurnitureListByUserUUId() {
        String userUUId = "648fdfbb54b0950d00c37c2e";
        Long lastSeenFurnitureId = 1L;
        Integer searchSize = 2;
        System.out.println(furnitureService.searchFurnitureListByUserUUId(userUUId, lastSeenFurnitureId, searchSize));
    }

    @Test
    public void searchFurnitureAllByUserUUId() {
        String userUUId = "648fdfbb54b0950d00c37c2f";
        System.out.println(furnitureService.searchFurnitureAllByUserUUId(userUUId));
    }

    @Test
    public void searchOneFurnitureByUserUUId() {
        String userUUId = "648fdfbb54b0950d00c37c2e";
        Long furnitureId = 2L;
        System.out.println(furnitureService.searchOneFurnitureByUserUUId(userUUId, furnitureId));
    }
}
