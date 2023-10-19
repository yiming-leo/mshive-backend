package com.yimingliao.mshivebackend.service.mongodb.impl;

import com.mongodb.client.result.UpdateResult;
import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.mongodb.Furniture;
import com.yimingliao.mshivebackend.mapper.mongodb.FurnitureRepository;
import com.yimingliao.mshivebackend.service.mongodb.IFurnitureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description Furniture 业务层实现类
 * @date 2023/10/19 15:23
 */
@Service
@Slf4j
public class FurnitureServiceImpl implements IFurnitureService {

    @Autowired
    private FurnitureRepository furnitureRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    //Insert One Furniture
    @Override
    public R insertOneFurniture(Furniture furniture) {
        //保存新增的家具
        Furniture saveFurniture = furnitureRepository.save(furniture);
        log.info("insertOneFurniture: " + furniture);
        //判定是否保存成功
        if ("".equals(saveFurniture.getId())) {
            return R.error(404, "Failed", new Date());
        }
        return R.success(200, "Success", new Date());
    }

    //Update One Furniture
    @Override
    public R updateOneFurniture(Furniture furniture) {
        //新建query
        Query query = new Query();
        //根据uuid查找furniture，这里where随便是id还是_id都可以，但是前端返回来的JSON必须和后端的Entity类相符合！
        query.addCriteria(Criteria.where("id").is(furniture.getId()));
        //更新furniture
        Update update = new Update().set("name", furniture.getName())
                .set("attribute", furniture.getAttribute())
                .set("mainColor", furniture.getMainColor())
                .set("minorColor", furniture.getMinorColor())
                .set("imgUrl", furniture.getImgUrl())
                .set("description", furniture.getDescription())
                .set("isBookmarks", furniture.getIsBookmarks())
                .set("refUserId", furniture.getRefUserId())
                .set("refRoomId", furniture.getRefRoomId());//多了refRoomId可变基
        //保存新增的房间
        log.info("updateOneFurniture: " + update);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Furniture.class);
        long modifiedCount = updateResult.getModifiedCount();
        //判定是否保存成功
        if (updateResult.getModifiedCount() == 0) {
            return R.error(404, "Update Failed", new Date(), modifiedCount);
        }
        return R.success(200, "Update Success", new Date(), modifiedCount);
    }

    //Delete One User's Furniture, need furnitureUUId
    @Override
    public R deleteOneFurnitureByFurnitureUUId(String furnitureUUId) {
        furnitureRepository.deleteById(furnitureUUId);
        log.info("deleteOneFurnitureByFurnitureUUId success");
        return R.success(200, "Success", new Date());
    }

    //Find One User's Some Furniture, need lastSeenFurnitureId & searchSize
    @Override
    public R searchFurnitureListByUserUUId(String userUUId, Long lastSeenFurnitureId, Integer searchSize) {
        Query query = new Query();
        //define the start ID using addCriteria
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        query.addCriteria(Criteria.where("furniture_id").gt(lastSeenFurnitureId));
        query.limit(searchSize);
        log.info("Furniture List Search Query: " + query);
        //Sort
        //Sort sort = Sort.by(order);
        //query.with(Sort.by(Sort.Direction.DESC))
        List<Furniture> furnitureList = mongoTemplate.find(query, Furniture.class);
        log.info("searchFurnitureListByUserUUId: " + furnitureList);
        return R.success(200, "Success", new Date(), furnitureList);
    }

    //Find One User's All Furniture
    @Override
    public R searchFurnitureAllByUserUUId(String userUUId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        List<Furniture> furnitureList = mongoTemplate.find(query, Furniture.class);
        if (furnitureList.isEmpty()) {
            return R.error(404, "Failed", new Date());
        }
        return R.success(200, "Success", new Date(), furnitureList);
    }

    //Find One User's One Furniture, need userUUId & furnitureId
    @Override
    public R searchOneFurnitureByUserUUId(String userUUId, Long furnitureId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        query.addCriteria(Criteria.where("furniture_id").is(furnitureId));
        List<Furniture> furnitureList = mongoTemplate.find(query, Furniture.class);
        if (furnitureList.isEmpty()) {
            return R.error(404, "Failed", new Date());
        }
        return R.success(200, "Success", new Date(), furnitureList);
    }
}
