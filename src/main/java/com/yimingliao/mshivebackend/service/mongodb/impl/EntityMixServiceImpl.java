package com.yimingliao.mshivebackend.service.mongodb.impl;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.mongodb.Furniture;
import com.yimingliao.mshivebackend.mapper.mongodb.RoomRepository;
import com.yimingliao.mshivebackend.service.mongodb.IEntityMixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description 混合实体类操作
 * @date 2023/10/19 15:23
 */
@Service
public class EntityMixServiceImpl implements IEntityMixService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    //--------------------Room--Furniture Related--------------------

    //Find One User's Furniture by his UserUUId & RoomUUId
    @Override
    public R searchOneFurnitureByUserUUIdRoomUUId(String userUUId, String roomUUId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        query.addCriteria(Criteria.where("ref_room_id").is(roomUUId));
        List<Furniture> furnitureList = mongoTemplate.find(query, Furniture.class);
        if (furnitureList.isEmpty()) {
            return R.error(404, "Failed", new Date());
        }
        return R.success(200, "Success", new Date(), furnitureList);
    }

    //--------------------Furniture--Stuff Related--------------------

}
